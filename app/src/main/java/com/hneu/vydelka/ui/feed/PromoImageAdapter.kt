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

class PromoImageAdapter(val onPromoClick:(PromoData) -> Unit) :
    ListAdapter<PromoImageAdapter.PromoData, PromoImageAdapter.PromoImageViewHolder>(PromoData.diffUtil) {
    data class PromoData(val promoTitle: String, val imageSrc: String, val promoId: Int) {
        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<PromoData>() {
                override fun areItemsTheSame(oldItem: PromoData, newItem: PromoData) =
                    oldItem === newItem

                override fun areContentsTheSame(oldItem: PromoData, newItem: PromoData) =
                    oldItem == newItem
            }
        }
    }

    inner class PromoImageViewHolder(private val maskableFrameLayout: View) :
        RecyclerView.ViewHolder(maskableFrameLayout) {
        fun bind(promoData: PromoData) {
            maskableFrameLayout.setOnClickListener { onPromoClick.invoke(promoData) }
            val imageView = maskableFrameLayout.findViewById<ImageView>(R.id.carousel_image_view)
            Glide.with(itemView.context).load(promoData.imageSrc).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PromoImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.carousel_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: PromoImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
