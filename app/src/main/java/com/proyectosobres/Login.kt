package com.proyectosobres

import DBcontrol
import android.content.Intent
import android.os.Bundle
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

        dbcontrol = DBcontrol(this)

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
                val isValidUser = checkUsuario(email, contrasena)
                if (isValidUser) {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuPrincipal::class.java).apply {
                        putExtra("correo", email)
                        putExtra("password", contrasena)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val clicka = findViewById<TextView>(R.id.clicka)
        clicka.setOnClickListener {
            val linkSignup = Intent(this, Signup::class.java)
            startActivity(linkSignup)
        }
    }
    //Inicio de sesion
    private fun checkUsuario(email: String, contrasena: String): Boolean {
        val db = dbcontrol.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM usuario WHERE correo = ? AND password = ?", arrayOf(email, contrasena))
        val isValidUser = cursor.count > 0
        cursor.close()
        return isValidUser
    }

    private fun mailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun contrasenaValida(password: String): Boolean {
        val patronContrasena = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,30}$"
        return password.matches(patronContrasena.toRegex())
    }
}