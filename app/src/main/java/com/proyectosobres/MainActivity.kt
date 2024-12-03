package com.proyectosobres

import DBcontrol
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backgroundImage = findViewById<ImageView>(R.id.backgroundImage)
        backgroundImage.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val dbControl = DBcontrol(this)
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            for (idJugador in 75..199) {
                try {
                    val datosJugador = dbControl.obtenerDatosJugador(idJugador)

                    if (datosJugador != null) {
                        Log.d("DatosJugador", "ID: $idJugador - Nombre: ${datosJugador["nombre_jugador"]}")
                        runOnUiThread {
                            generarCarta(datosJugador, this)
                        }
                    } else {
                        Log.e("DatosJugador", "Jugador con ID $idJugador no encontrado")
                    }
                } catch (e: Exception) {
                    Log.e("DatosJugador", "Error al obtener los datos del jugador con ID $idJugador", e)
                }
            }
        }
    }

    fun generarCarta(datosJugador: Map<String, String>, context: Context) {
        val ancho = 700
        val alto = 700
        val bitmap = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

        // Fondo principal
        val rareza = datosJugador["rareza"] ?: "común"
        val colorFondo = when (rareza.lowercase()) {
            "común" -> Color.parseColor("#BDBDBD")
            "raro" -> Color.parseColor("#2196F3")
            "épica" -> Color.parseColor("#9C27B0")
            "leyenda" -> Color.parseColor("#D4AF37")
            else -> Color.parseColor("#FFFFFF")
        }
        paint.color = colorFondo
        canvas.drawRect(0f, 0f, ancho.toFloat(), alto.toFloat(), paint)

        paint.color = Color.BLACK
        paint.strokeWidth = 10f
        paint.style = Paint.Style.STROKE
        canvas.drawRect(5f, 5f, (ancho - 5).toFloat(), (alto - 5).toFloat(), paint)
        paint.style = Paint.Style.FILL // Restaurar estilo

        // Imagen del jugador
        try {
            val imagenJugador = datosJugador["foto_jugador"]?.let {
                BitmapFactory.decodeStream(context.assets.open(it))
            }

            imagenJugador?.let {
                val jugadorScaled = Bitmap.createScaledBitmap(it, ancho - 20, alto / 2 - 20, false)
                canvas.drawBitmap(jugadorScaled, 10f, 10f, null)
            }
        } catch (e: IOException) {
            Log.e("GenerarCarta", "Error al cargar la imagen del jugador", e)
        }

        // Dibujar el nombre del jugador
        paint.color = Color.WHITE
        paint.textSize = 50f
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.setShadowLayer(10f, 5f, 5f, Color.BLACK)

        val nombreJugador = datosJugador["nombre_jugador"] ?: "Desconocido"
        val textoXStart = 220f // Espacio desde el inicio para las palabras
        val nombreY = alto / 2f + 50f // Colocar el nombre justo debajo de la mitad superior
        canvas.drawText(nombreJugador, textoXStart, nombreY, paint)

        // Logo del equipo
        val logoWidth = 175
        val logoHeight = 175
        val espacioEntreLogoYPalabras = 20f // Margen entre el logo y las palabras

        try {
            val logoEquipo = datosJugador["logo"]?.let {
                BitmapFactory.decodeStream(context.assets.open(it))
            }

            logoEquipo?.let {
                val logoScaled = Bitmap.createScaledBitmap(it, logoWidth, logoHeight, false)
                val logoX = 20f
                val logoY = alto / 2f + 60f // Ajustar para bajar el logo más
                canvas.drawBitmap(logoScaled, logoX, logoY, null)

                // Texto al lado del logo (centrado verticalmente respecto al logo)
                val textoCentroY = logoY + (logoHeight / 2f) +20f// Centrar texto verticalmente respecto al logo
                paint.textSize = 40f
                paint.setShadowLayer(5f, 2f, 2f, Color.BLACK)

                val estadisticasTexto = listOf(
                    "Edad: ${datosJugador["edad"] ?: "N/A"}",
                    "Posición: ${datosJugador["posicion"] ?: "N/A"}",
                    "Rareza: ${datosJugador["rareza"] ?: "N/A"}"
                )

                estadisticasTexto.forEachIndexed { index, texto ->
                    val textoY = textoCentroY + (index - 1) * (paint.textSize + espacioEntreLogoYPalabras ) // Ajustar espacio entre líneas
                    canvas.drawText(texto, textoXStart, textoY, paint)
                }
            }
        } catch (e: IOException) {
            Log.e("GenerarCarta", "Error al cargar el logo del equipo", e)
        }

        // Guardar la carta
        val archivoSalida = File(context.getExternalFilesDir(null), "cartas/carta_${datosJugador["nombre_jugador"]}.png")
        archivoSalida.parentFile?.mkdirs()

        try {
            val fos = FileOutputStream(archivoSalida)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            Log.d("GenerarCarta", "Carta guardada en ${archivoSalida.absolutePath}")
        } catch (e: IOException) {
            Log.e("GenerarCarta", "Error al guardar la carta", e)
        }
    }
}