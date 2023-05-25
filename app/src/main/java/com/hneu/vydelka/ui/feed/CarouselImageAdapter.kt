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

class CarouselImageAdapter(val useWideItems:Boolean = false, val onPromoClick:(CarouselData) -> Unit) :
    ListAdapter<CarouselImageAdapter.CarouselData, CarouselImageAdapter.PromoImageViewHolder>(CarouselData.diffUtil) {
    data class CarouselData(val promoTitle: String, val imageSrc: String, val promoId: Int) {
        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<CarouselData>() {
                override fun areItemsTheSame(oldItem: CarouselData, newItem: CarouselData) =
                    oldItem === newItem

                override fun areContentsTheSame(oldItem: CarouselData, newItem: CarouselData) =
                    oldItem == newItem
            }
        }
    }

    inner class PromoImageViewHolder(private val maskableFrameLayout: View) :
        RecyclerView.ViewHolder(maskableFrameLayout) {
        fun bind(promoData: CarouselData) {
            maskableFrameLayout.setOnClickListener { onPromoClick.invoke(promoData) }
            val imageView = maskableFrameLayout.findViewById<ImageView>(R.id.carousel_image_view)
            Glide.with(itemView.context).load(promoData.imageSrc).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PromoImageViewHolder(
            LayoutInflater.from(parent.context).inflate(if(useWideItems) R.layout.carousel_list_item_wide else R.layout.carousel_list_item_small, parent, false)
        )

    override fun onBindViewHolder(holder: PromoImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
