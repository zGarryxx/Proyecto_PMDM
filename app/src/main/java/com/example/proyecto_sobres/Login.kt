package com.example.proyecto_sobres

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_estilo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mail = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.Contraseña)
        val botonAcceder = findViewById<Button>(R.id.btnAcceder)

        botonAcceder.setOnClickListener {
            val email = mail.text.toString()
            val contrasena = password.text.toString()

            if (!mailValido(email)) {
                Toast.makeText(this, "Por favor, introduce un email válido", Toast.LENGTH_SHORT).show()
            } else if (!contrasenaValida(contrasena)) {
                Toast.makeText(this, "La contraseña debe tener entre 8 y 30 caracteres, incluyendo números, letras mayúsculas y minúsculas", Toast.LENGTH_LONG).show()
            } else {
                val linkMenu = Intent(this, MenuPrincipal::class.java)
                startActivity(linkMenu)
            }
        }

        val clicka = findViewById<TextView>(R.id.clicka)
        clicka.setOnClickListener {
            val linkSignup = Intent(this, Signup::class.java)
            startActivity(linkSignup)
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