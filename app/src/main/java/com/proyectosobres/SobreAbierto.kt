package com.proyectosobres


import android.app.Activity
import android.os.Bundle
import android.widget.ImageView

class SobreAbierto : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sobreabierto_estilo)

        // Obtener las imagenes
        val imagen1 = findViewById<ImageView>(R.id.imagen1)
        val imagen2 = findViewById<ImageView>(R.id.imagen2)
        val imagen3 = findViewById<ImageView>(R.id.imagen3)
        val imagen4 = findViewById<ImageView>(R.id.imagen4)
        val imagen5 = findViewById<ImageView>(R.id.imagen5)

        // Añadir listener para la ocultacion de estas
        imagen1.setOnClickListener {
            imagen1.visibility = ImageView.INVISIBLE
        }

        imagen2.setOnClickListener {
            imagen2.visibility = ImageView.INVISIBLE
        }

        imagen3.setOnClickListener {
            imagen3.visibility = ImageView.INVISIBLE
        }

        imagen4.setOnClickListener {
            imagen4.visibility = ImageView.INVISIBLE
        }

        imagen5.setOnClickListener {
            imagen5.visibility = ImageView.INVISIBLE
        }
    }
}
