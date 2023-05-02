package com.hneu.vydelka.ui.feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hneu.vydelka.R

class PromoImageAdapter() :
    ListAdapter<PromoImageAdapter.ImageData, PromoImageAdapter.PromoImageViewHolder>(ImageData.diffUtil) {
    data class ImageData(val imageSrc: String) {
        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<ImageData>() {
                override fun areItemsTheSame(oldItem: ImageData, newItem: ImageData) =
                    oldItem === newItem

                override fun areContentsTheSame(oldItem: ImageData, newItem: ImageData) =
                    oldItem == newItem
            }
        }
    }

    class PromoImageViewHolder(private val maskableFrameLayout: View) :
        RecyclerView.ViewHolder(maskableFrameLayout) {
        fun bind(imageSrc: String) {
            val imageView = maskableFrameLayout.findViewById<ImageView>(R.id.carousel_image_view)
            Glide.with(itemView.context).load(imageSrc).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PromoImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.carousel_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: PromoImageViewHolder, position: Int) {
        holder.bind(getItem(position).imageSrc)
    }
}
