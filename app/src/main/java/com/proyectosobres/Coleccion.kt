package com.proyectosobres

import DBcontrol
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coleccion : Activity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private lateinit var spinnerFilter: Spinner
    private lateinit var dbControl: DBcontrol
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coleccion_estilo)

        recyclerView = findViewById(R.id.recyclerView)
        spinnerFilter = findViewById(R.id.spinnerFilter)
        dbControl = DBcontrol(this)

        recyclerView.layoutManager = GridLayoutManager(this, 3)

        userId = intent.getIntExtra("userId", -1)

        val images = getAllCards()
        adapter = ImageAdapter(this, images)
        recyclerView.adapter = adapter

        val filters = arrayOf("Todas", "Común", "Raro", "Épica", "Leyenda")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filters)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter = spinnerAdapter

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val rarity = when (position) {
                    0 -> "Todas"
                    1 -> "comun"
                    2 -> "raro"
                    3 -> "épica"
                    4 -> "leyenda"
                    else -> "Todas"
                }
                Log.d("Coleccion", "Selected rarity: $rarity")
                val images = when (position) {
                    0 -> getAllCards()
                    1 -> getCardsByRarity("comun")
                    2 -> getCardsByRarity("raro")
                    3 -> getCardsByRarity("épica")
                    4 -> getCardsByRarity("leyenda")
                    else -> getAllCards()
                }
                Log.d("Coleccion", "Images: $images")
                adapter.updateData(images)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }

        val coleccion: ImageView = findViewById(R.id.coleccion)
        coleccion.setOnClickListener {
            val linkColeccion = Intent(this, Coleccion::class.java)
            linkColeccion.putExtra("userId", userId)
            startActivity(linkColeccion)
        }

        val home: ImageView = findViewById(R.id.home)
        home.setOnClickListener {
            val linkMenuPrincipal = Intent(this, MenuPrincipal::class.java)
            linkMenuPrincipal.putExtra("userId", userId)
            startActivity(linkMenuPrincipal)
        }

        val login: ImageView = findViewById(R.id.login)
        login.setOnClickListener {
            val linkLogin = Intent(this, Login::class.java)
            linkLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(linkLogin)
            finish()
        }
    }

    private fun getAllCards(): List<Pair<String, Boolean>> {
        return dbControl.getAllCards(userId)
    }

    private fun getCardsByRarity(rarity: String): List<Pair<String, Boolean>> {
        return dbControl.getAllCards(userId).filter { card ->
            dbControl.getCardsByRarity(rarity).contains(card.first)
        }
    }
}