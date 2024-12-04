package com.proyectosobres

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MenuPrincipal : Activity() {
    private lateinit var timerTextView: TextView
    private lateinit var btnAbrirSobre: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var timeLeftInMillis: Long = 3 * 60 * 1000
    private var countDownTimer: CountDownTimer? = null

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menup_estilos)

        timerTextView = findViewById(R.id.timerTextView)
        btnAbrirSobre = findViewById(R.id.btnAbrirSobre)
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)

        // Restaurar tiempo desde SharedPreferences
        val endTime = sharedPreferences.getLong("endTime", 0)
        if (endTime > 0) {
            timeLeftInMillis = endTime - System.currentTimeMillis()
            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0
            }
        }

        val coleccion: ImageView = findViewById(R.id.coleccion)
        coleccion.setOnClickListener {
            saveTimeRemaining()
            val linkColeccion = Intent(this, Coleccion::class.java)
            startActivity(linkColeccion)
        }

        val home: ImageView = findViewById(R.id.home)
        home.setOnClickListener {
            saveTimeRemaining()
            val linkMenuPrincipal = Intent(this, MenuPrincipal::class.java)
            startActivity(linkMenuPrincipal)
        }

        val login: ImageView = findViewById(R.id.login)
        login.setOnClickListener {
            saveTimeRemaining()
            val linkLogin = Intent(this, Login::class.java)
            startActivity(linkLogin)
        }

        val abrirSobre: Button = findViewById(R.id.btnAbrirSobre)
        abrirSobre.setOnClickListener {
            if (timeLeftInMillis == 0L) {
                // Reiniciar el temporizador y abrir el sobre
                resetTimerAndOpenSobre()
            } else {
                saveTimeRemaining()
                val linkAbrirSobre = Intent(this, AperturaSobre::class.java)
                startActivity(linkAbrirSobre)
            }
        }

        startCountDownTimer()
    }

    override fun onResume() {
        super.onResume()
        // Restaurar tiempo restante al volver a la app
        val endTime = sharedPreferences.getLong("endTime", 0)
        if (endTime > 0) {
            timeLeftInMillis = endTime - System.currentTimeMillis()
            if (timeLeftInMillis < 0) {
                timeLeftInMillis = 0
            }
        }
        startCountDownTimer()
    }

    override fun onPause() {
        super.onPause()
        saveTimeRemaining()
    }

    private fun startCountDownTimer() {
        countDownTimer?.cancel()
        btnAbrirSobre.isEnabled = timeLeftInMillis == 0L

        if (timeLeftInMillis > 0) {
            countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeLeftInMillis = millisUntilFinished
                    val minutes = (millisUntilFinished / (60 * 1000)) % 60
                    val seconds = (millisUntilFinished / 1000) % 60
                    timerTextView.text = String.format("Tiempo para abrir el sobre: %02d:%02d", minutes, seconds)
                }

                override fun onFinish() {
                    timeLeftInMillis = 0
                    timerTextView.text = "¡Puedes abrir el sobre!"
                    btnAbrirSobre.isEnabled = true
                }
            }.start()
        } else {
            timerTextView.text = "¡Puedes abrir el sobre!"
            btnAbrirSobre.isEnabled = true
        }
    }

    private fun saveTimeRemaining() {
        countDownTimer?.cancel()
        val endTime = System.currentTimeMillis() + timeLeftInMillis
        val editor = sharedPreferences.edit()
        editor.putLong("endTime", endTime)
        editor.apply()
    }

    private fun resetTimerAndOpenSobre() {
        // Reiniciar el temporizador
        timeLeftInMillis = 3 * 60 * 1000
        saveTimeRemaining()

        // Iniciar la actividad "AperturaSobre"
        val linkAbrirSobre = Intent(this, AperturaSobre::class.java)
        startActivity(linkAbrirSobre)

        // Reiniciar el temporizador
        startCountDownTimer()
    }
}
