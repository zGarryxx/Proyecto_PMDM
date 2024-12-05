package com.proyectosobres

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
        val userId = intent.getIntExtra("userId", -1)
        Log.d("MenuPrincipal", "userId: $userId")

        timerTextView = findViewById(R.id.timerTextView)
        btnAbrirSobre = findViewById(R.id.btnAbrirSobre)
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)

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
            linkColeccion.putExtra("userId", userId) // Pass userId to Coleccion
            startActivity(linkColeccion)
        }

        val home: ImageView = findViewById(R.id.home)
        home.setOnClickListener {
            saveTimeRemaining()
            val linkMenuPrincipal = Intent(this, MenuPrincipal::class.java)
            linkMenuPrincipal.putExtra("userId", userId) // Pass userId to MenuPrincipal
            startActivity(linkMenuPrincipal)
        }

        val login: ImageView = findViewById(R.id.login)
        login.setOnClickListener {
            saveTimeRemaining()
            val linkLogin = Intent(this, Login::class.java)
            linkLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(linkLogin)
            finish()
        }

        val abrirSobre: Button = findViewById(R.id.btnAbrirSobre)
        abrirSobre.setOnClickListener {
            if (timeLeftInMillis == 0L) {
                resetTimerAndOpenSobre(userId)
            } else {
                saveTimeRemaining()
                val linkAbrirSobre = Intent(this, SobreAbierto::class.java)
                linkAbrirSobre.putExtra("userId", userId) // Pass userId to SobreAbierto
                startActivity(linkAbrirSobre)
            }
        }

        startCountDownTimer()
    }

    override fun onResume() {
        super.onResume()
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

    private fun resetTimerAndOpenSobre(userId: Int) {

        timeLeftInMillis = 3 * 60 * 1000
        saveTimeRemaining()


        val linkAbrirSobre = Intent(this, AperturaSobre::class.java)
        linkAbrirSobre.putExtra("userId", userId) // Pass userId to SobreAbierto
        startActivity(linkAbrirSobre)

        startCountDownTimer()
    }
}