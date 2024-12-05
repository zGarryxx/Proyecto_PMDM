package com.proyectosobres

import DBcontrol
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

class SobreAbierto : Activity() {
    private var invisibleCount = 0
    private var nextImageIndex = 4

    // En SobreAbierto.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sobreabierto_estilo)

        val dbControl = DBcontrol(this)
        val imageViews = listOf(
            findViewById<ImageView>(R.id.image1),
            findViewById<ImageView>(R.id.image2),
            findViewById<ImageView>(R.id.image3),
            findViewById<ImageView>(R.id.image4),
            findViewById<ImageView>(R.id.image5)
        )

        val userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            // Handle the error case where userId is not passed correctly
            Log.e("SobreAbierto", "userId not found in Intent")
            return
        }

        // Obtener 5 cartas aleatorias
        val cartas = dbControl.getRandomCartasWithIds(5)

        // Guardar las cartas en la tabla usuario_cartas
        for (carta in cartas) {
            dbControl.assignCartaToUsuario(userId.toLong(), carta.first.toLong())
        }

        // Mostrar las imágenes en los ImageViews
        for (i in cartas.indices) {
            val imagePath = "file:///android_asset/${cartas[i].second}"
            Glide.with(this)
                .load(imagePath)
                .placeholder(R.drawable.carta_menup)
                .error(R.drawable.carta_menup)
                .into(imageViews[i])

            imageViews[i].setOnClickListener {
                if (i == nextImageIndex) {
                    it.visibility = ImageView.INVISIBLE // Hacer invisible la imagen cuando se toque
                    invisibleCount++
                    nextImageIndex--
                    if (invisibleCount == 5) {
                        val intent = Intent(this, MenuPrincipal::class.java)
                        intent.putExtra("userId", userId) // Pass userId to MenuPrincipal
                        startActivity(intent)
                    }
                }
            }
        }
    }
}