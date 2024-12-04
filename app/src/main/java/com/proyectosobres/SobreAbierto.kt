package com.proyectosobres

import DBcontrol
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class SobreAbierto : Activity() {
    private var invisibleCount = 0
    private var nextImageIndex = 4

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

        // Obtener 5 cartas aleatorias
        val images = dbControl.getRandomCartas(5)

        // Mostrar las imágenes en los ImageViews
        for (i in images.indices) {
            val imagePath = "file:///android_asset/${images[i]}"
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
                        startActivity(intent)
                    }
                }
            }
        }
    }
}