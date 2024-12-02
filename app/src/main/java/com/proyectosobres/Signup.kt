package com.proyectosobres


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.Activity

class Signup : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_estilo)

        val mail = findViewById<EditText>(R.id.Email)
        val contrasena = findViewById<EditText>(R.id.Contraseña)
        val repiteContrasena = findViewById<EditText>(R.id.nuevaContraseña)
        val botonCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        botonCrearCuenta.setOnClickListener {
            val email = mail.text.toString()
            val password = contrasena.text.toString()
            val repitePassword = repiteContrasena.text.toString()

            if (email.isEmpty() || password.isEmpty() || repitePassword.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!mailValido(email)) {
                Toast.makeText(this, "Por favor, introduce un email válido", Toast.LENGTH_SHORT).show()
            } else if (!contrasenaValida(password)) {
                Toast.makeText(this, "La contraseña debe tener entre 8 y 30 caracteres, incluyendo números, letras mayúsculas y minúsculas", Toast.LENGTH_LONG).show()
            } else if (password != repitePassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                val linkMenu = Intent(this, MenuPrincipal::class.java)
                startActivity(linkMenu)
            }
        }

        val clicka = findViewById<TextView>(R.id.clicka)
        clicka.setOnClickListener {
            val linkLogin = Intent(this, Login::class.java)
            startActivity(linkLogin)
        }
    }

    private fun mailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun contrasenaValida(password: String): Boolean {
        val patronContrasena = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}$"
        return password.matches(patronContrasena.toRegex())
    }
}