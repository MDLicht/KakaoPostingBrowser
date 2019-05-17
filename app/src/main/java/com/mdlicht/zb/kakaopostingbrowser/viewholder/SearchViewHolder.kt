package com.mdlicht.zb.kakaopostingbrowser.viewholder

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.activity.DetailActivity
import com.mdlicht.zb.kakaopostingbrowser.common.Constants
import com.mdlicht.zb.kakaopostingbrowser.databinding.ItemCardBinding
import com.mdlicht.zb.kakaopostingbrowser.model.Document
import com.mdlicht.zb.kakaopostingbrowser.util.PreferenceUtil

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding: ItemCardBinding = DataBindingUtil.bind(itemView)!!

    companion object {
        fun newInstance(parent: ViewGroup): SearchViewHolder {
            return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
        }
    }

    fun onBind(position: Int, item: Document?) {
        binding.document = item
        binding.root.setOnClickListener {
            it.context.startActivity(Intent(it.context, DetailActivity::class.java).apply {
                putExtra(Constants.KEY_DOCUMENT, item)
            })
        }
        if(PreferenceUtil.isVisited(binding.root.context, item!!.url!!)) {
            binding.root.setBackgroundColor(itemView.context.resources.getColor(R.color.color_visited))
        } else {
            binding.root.setBackgroundColor(Color.WHITE)
        }
    }
}