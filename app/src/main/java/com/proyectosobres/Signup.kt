package com.proyectosobres

import DBcontrol
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.Activity
import android.content.ContentValues
import android.util.Log

class Signup : Activity() {

    private lateinit var dbcontrol: DBcontrol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_estilo)

        // Inicializar la base de datos
        dbcontrol = DBcontrol(this)

        val mail = findViewById<EditText>(R.id.Email)
        val contrasena = findViewById<EditText>(R.id.Contraseña)
        val repiteContrasena = findViewById<EditText>(R.id.nuevaContraseña)
        val botonCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        // Condición para crear una cuenta
        botonCrearCuenta.setOnClickListener {
            val email = mail.text.toString()
            val password = contrasena.text.toString()
            val repitePassword = repiteContrasena.text.toString()

            // Comprobación de campos vacíos
            if (email.isEmpty() || password.isEmpty() || repitePassword.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!mailValido(email)) {
                Toast.makeText(this, "Por favor, introduce un email válido", Toast.LENGTH_SHORT).show()
            } else if (!contrasenaValida(password)) {
                Toast.makeText(this, "La contraseña debe tener entre 8 y 30 caracteres, incluyendo números, letras mayúsculas y minúsculas", Toast.LENGTH_LONG).show()
            } else if (password != repitePassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {

                // Crear usuario en la base de datos
                val resultado = crearUsuario(email, password)
                if (resultado != -1L) {
                    Toast.makeText(this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show()
                    val linkMenu = Intent(this, MenuPrincipal::class.java)
                    startActivity(linkMenu)
                } else {
                    Toast.makeText(this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Enlace a la pantalla de inicio de sesión
        val clicka = findViewById<TextView>(R.id.clicka)
        clicka.setOnClickListener {
            val linkLogin = Intent(this, Login::class.java)
            startActivity(linkLogin)
        }
    }

    // Crear usuario
    // Crear usuario
    private fun crearUsuario(email: String, contrasena: String): Long {
        return dbcontrol.insertUsuario(email, contrasena)
    }

    // Comprobar si el email ya está registrado
    private fun mailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Comprobar si la contraseña es válida
    private fun contrasenaValida(password: String): Boolean {
        val patronContrasena = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}$"
        return password.matches(patronContrasena.toRegex())
    }
}