package com.proyectosobres

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MenuPrincipal : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menup_estilos)

        val coleccion: ImageView = findViewById(R.id.coleccion)
        coleccion.setOnClickListener {
            val linkColeccion = Intent(this, Coleccion::class.java)
            startActivity(linkColeccion)
        }

        val home: ImageView = findViewById(R.id.home)
        home.setOnClickListener {
            val linkMenuPrincipal = Intent(this, MenuPrincipal::class.java)
            startActivity(linkMenuPrincipal)
        }

        val tienda: ImageView = findViewById(R.id.tienda)
        tienda.setOnClickListener {
            val linkTienda = Intent(this, Tienda::class.java)
            startActivity(linkTienda)
        }

        val abrirSobre: Button = findViewById(R.id.btnAbrirSobre)
        abrirSobre.setOnClickListener {
            val linkAbrirSobre = Intent(this, SobreAbierto::class.java)
            startActivity(linkAbrirSobre)
        }
    }

}