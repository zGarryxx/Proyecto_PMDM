package com.proyectosobres

import DBcontrol
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Coleccion : Activity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
    private lateinit var spinnerFilter: Spinner
    private lateinit var dbControl: DBcontrol

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coleccion_estilo)

        recyclerView = findViewById(R.id.recyclerView)
        spinnerFilter = findViewById(R.id.spinnerFilter)
        dbControl = DBcontrol(this)

        recyclerView.layoutManager = GridLayoutManager(this, 3)

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
    }

    private fun getAllCards(): List<String> {
        return dbControl.getAllCards()
    }

    private fun getCardsByRarity(rarity: String): List<String> {
        return dbControl.getCardsByRarity(rarity)
    }
}