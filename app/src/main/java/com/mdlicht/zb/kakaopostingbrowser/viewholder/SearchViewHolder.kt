package com.mdlicht.zb.kakaopostingbrowser.viewholder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.databinding.ItemCardBinding

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding: ItemCardBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): SearchViewHolder {
            return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
        }
    }
}