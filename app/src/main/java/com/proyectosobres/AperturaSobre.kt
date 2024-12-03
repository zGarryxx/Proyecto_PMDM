package com.proyectosobres

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AperturaSobre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.apertura_sobre)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sobre: ImageView = findViewById(R.id.sobre)

        sobre.setOnClickListener {
            val animacionzoom = AnimationUtils.loadAnimation(this, R.anim.animacion_sobre)

            animacionzoom.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                }

                override fun onAnimationEnd(animation: Animation) {
                    val intent = Intent(this@AperturaSobre, SobreAbierto::class.java)
                    startActivity(intent)
                }

                override fun onAnimationRepeat(animation: Animation) {
                }
            })
            sobre.startAnimation(animacionzoom)
        }
    }
}