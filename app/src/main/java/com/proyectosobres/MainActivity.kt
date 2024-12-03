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
        val db = dbControl.writableDatabase
        val equipoNombre = "Valladolid"
        val equipoId = dbControl.obtenerEquipoId(equipoNombre) ?: dbControl.insertEquipo(
            nombreEquipo = equipoNombre,
            numeroDeJugadores = 25,
            comunidadAutonoma = "Castilla y León",
            clasificacion = 20,
            logo = "valladolid_escudo.png"
        )

        if (equipoId != -1L) {
            Log.d("DBcontrol", "Equipo Valladolid insertado con ID: $equipoId")
        } else {
            Log.e("DBcontrol", "Error al insertar el equipo Valladolid")
        }

// Lista de jugadores del equipo "Valladolid"
        val jugadores = listOf(
            Triple("Sergio León", "Delantero", "Común"),
            Triple("Óscar Plano", "Mediocampo", "Común"),
            Triple("Javi Sánchez", "Defensa", "Épica"),
            Triple("Jordi Masip", "Portero", "Común"),
            Triple("Monchu", "Mediocampo", "Común")
        )

// Inserción de jugadores para el equipo "Valladolid"
        jugadores.forEachIndexed { index, jugador ->
            val jugadorId = dbControl.insertJugador(
                nombreJugador = jugador.first,
                dorsal = when (index) {
                    0 -> 7
                    1 -> 10
                    2 -> 24
                    3 -> 1
                    4 -> 14
                    else -> (1..99).random()
                },
                posicion = jugador.second,
                rareza = jugador.third,
                equipoId = equipoId.toInt(),
                edad = when (index) {
                    0 -> 35
                    1 -> 33
                    2 -> 27
                    3 -> 35
                    4 -> 25
                    else -> (20..40).random()
                },
                fotoJugador = "${jugador.first.replace(" ", "")}Valladolid.png"
            )

            if (jugadorId != -1L) {
                Log.d("DBcontrol", "Jugador ${jugador.first} insertado con ID: $jugadorId")
            } else {
                Log.e("DBcontrol", "Error al insertar el jugador ${jugador.first}")
            }
        }

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute {
            for (idJugador in 80..199) {
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
        val ancho = 600
        val alto = 700
        val bitmap = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()

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

        try {
            val imagenJugador = datosJugador["foto_jugador"]?.let {
                BitmapFactory.decodeStream(context.assets.open(it))
            }

            imagenJugador?.let {
                val jugadorScaled = Bitmap.createScaledBitmap(it, ancho, alto / 2, false)
                canvas.drawBitmap(jugadorScaled, 0f, 0f, null)
            }
        } catch (e: IOException) {
            Log.e("GenerarCarta", "Error al cargar la imagen del jugador", e)
        }

        var logoBottom = 0f
        try {
            val logoEquipo = datosJugador["logo"]?.let {
                BitmapFactory.decodeStream(context.assets.open(it))
            }

            logoEquipo?.let {
                val logoScaled = Bitmap.createScaledBitmap(it, 120, 120, false)
                val logoX = 20f
                val logoY = alto / 2f + 10f
                canvas.drawBitmap(logoScaled, logoX, logoY, null)
                logoBottom = logoY + 120f
            }
        } catch (e: IOException) {
            Log.e("GenerarCarta", "Error al cargar el logo del equipo", e)
        }

        val textoInicioY = logoBottom + 50f
        paint.color = Color.WHITE
        paint.textSize = 60f
        paint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText(datosJugador["nombre_jugador"] ?: "Desconocido", 50f, textoInicioY, paint)

        paint.textSize = 40f
        val estadisticasInicioY = textoInicioY + 60f
        canvas.drawText("Edad: ${datosJugador["edad"]}", 50f, estadisticasInicioY, paint)
        canvas.drawText("Posición: ${datosJugador["posicion"]}", 50f, estadisticasInicioY + 50f, paint)
        canvas.drawText("Rareza: ${datosJugador["rareza"]}", 50f, estadisticasInicioY + 100f, paint)

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