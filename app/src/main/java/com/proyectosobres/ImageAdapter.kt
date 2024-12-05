package com.proyectosobres

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(private val context: Context, private var cards: List<Pair<String, Boolean>>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (image, isCollected) = cards[position]
        val imagePath = "file:///android_asset/$image"

        Glide.with(context)
            .load(imagePath)
            .placeholder(R.drawable.carta_menup)
            .error(R.drawable.carta_menup)
            .into(holder.imageView)

        if (!isCollected) {
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)
            val filter = ColorMatrixColorFilter(matrix)
            holder.imageView.colorFilter = filter
        } else {
            holder.imageView.clearColorFilter()
        }
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun updateData(newCards: List<Pair<String, Boolean>>) {
        cards = newCards
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}