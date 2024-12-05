package com.proyectosobres

import DBcontrol
import android.app.Activity
import android.os.Bundle
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

        val filters = arrayOf("Todas", "comun", "raro", "épica", "leyenda")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filters)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter = spinnerAdapter

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (position) {
                    0 -> adapter.updateData(getAllCards())
                    1 -> adapter.updateData(getCardsByRarity("comun"))
                    2 -> adapter.updateData(getCardsByRarity("raro"))
                    3 -> adapter.updateData(getCardsByRarity("épica"))
                    4 -> adapter.updateData(getCardsByRarity("leyenda"))
                }
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