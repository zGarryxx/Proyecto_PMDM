package com.proyectosobres

import DBcontrol
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    private lateinit var dbcontrol: DBcontrol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_estilo)

        // Inicializar la base de datos
        dbcontrol = DBcontrol(this)

        val mail = findViewById<EditText>(R.id.Email)
        val password = findViewById<EditText>(R.id.Contraseña)
        val botonAcceder = findViewById<Button>(R.id.btnAcceder)

        // Condición para acceder a la aplicación
        botonAcceder.setOnClickListener {
            val email = mail.text.toString()
            val contrasena = password.text.toString()

            if (!mailValido(email)) {
                Toast.makeText(this, "Por favor, introduce un email válido", Toast.LENGTH_SHORT).show()
            } else if (!contrasenaValida(contrasena)) {
                Toast.makeText(this, "La contraseña debe tener entre 8 y 30 caracteres, incluyendo números, letras mayúsculas y minúsculas", Toast.LENGTH_LONG).show()
            } else {
                val userId = comprobarUsuario(email, contrasena)
                if (userId != null) {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuPrincipal::class.java).apply {
                        putExtra("userId", userId)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Enlace a la pantalla de registro
        val clicka = findViewById<TextView>(R.id.clicka)
        clicka.setOnClickListener {
            val linkSignup = Intent(this, Signup::class.java)
            startActivity(linkSignup)
        }
    }

    private fun comprobarUsuario(email: String, contrasena: String): Int? {
        val db = dbcontrol.readableDatabase
        val cursor = db.rawQuery("SELECT id FROM usuarios WHERE correo = ? AND password = ?", arrayOf(email, contrasena))
        var userId: Int? = null
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
        }
        cursor.close()

        // Registro de depuración
        Log.d("Login", "Email: $email, Contraseña: $contrasena, Usuario válido: ${userId != null}")

        return userId
    }


    // Validar email
    private fun mailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validar contraseña
    private fun contrasenaValida(password: String): Boolean {
        val patronContrasena = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}$"
        return password.matches(patronContrasena.toRegex())
    }
}