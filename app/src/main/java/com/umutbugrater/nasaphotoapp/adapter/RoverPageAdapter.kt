package com.umutbugrater.nasaphotoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.umutbugrater.nasaphotoapp.R
import com.umutbugrater.nasaphotoapp.model.PhotoDto
import com.umutbugrater.nasaphotoapp.service.RecyclerViewClick
import kotlinx.android.synthetic.main.item_row.view.*

class RoverPageAdapter(private val photos: List<PhotoDto>, recyclerViewClick: RecyclerViewClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var click = recyclerViewClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoHolder -> {
                holder.bind(photos[position])

                holder.itemView.setOnClickListener {
                    click.openWindow(photos[position])
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class PhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photoDto: PhotoDto) {
            val image = photoDto.imgSrc.replace("http", "https")
            Glide.with(itemView.context).load(image).into(itemView.item_img)
            itemView.item_data1_carName.text = photoDto.rover.name
            itemView.item_data2_earthDate.text = photoDto.earthDate
            itemView.item_data3_cameraName.text = photoDto.camera.name
        }
    }

}