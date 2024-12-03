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

        // Enlace a la colección
        coleccion.setOnClickListener {
            val linkColeccion = Intent(this, Coleccion::class.java)
            startActivity(linkColeccion)
        }

        // Enlace al menú principal
        val home: ImageView = findViewById(R.id.home)
        home.setOnClickListener {
            val linkMenuPrincipal = Intent(this, MenuPrincipal::class.java)
            startActivity(linkMenuPrincipal)
        }

        // Enlace al login
        val login: ImageView = findViewById(R.id.login)
        login.setOnClickListener {
            val linkLogin = Intent(this, Login::class.java)
            startActivity(linkLogin)
        }

        // Enlace a la pantalla para Abrir Sobre
        val abrirSobre: Button = findViewById(R.id.btnAbrirSobre)
        abrirSobre.setOnClickListener {
            val linkAbrirSobre = Intent(this, AperturaSobre::class.java)
            startActivity(linkAbrirSobre)
        }
    }

}