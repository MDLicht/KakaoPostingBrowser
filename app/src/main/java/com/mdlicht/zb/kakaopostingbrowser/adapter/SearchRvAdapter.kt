package com.mdlicht.zb.kakaopostingbrowser.adapter

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.ViewGroup
import com.mdlicht.zb.kakaopostingbrowser.activity.DetailActivity
import com.mdlicht.zb.kakaopostingbrowser.common.Constants
import com.mdlicht.zb.kakaopostingbrowser.model.Document
import com.mdlicht.zb.kakaopostingbrowser.util.PreferenceUtil
import com.mdlicht.zb.kakaopostingbrowser.viewholder.SearchViewHolder

class SearchRvAdapter: RecyclerView.Adapter<SearchViewHolder>() {
    private val _dataSet: MutableList<Document?> = mutableListOf()
    private var dataSet: List<Document?>? = _dataSet

    var filter = 0
        set(value) {
            field = value
            filterChanged()
        }
    var order = 0
        set(value) {
            field = value
            filterChanged()
        }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchViewHolder {
        return SearchViewHolder.newInstance(p0)
    }

    override fun getItemCount(): Int {
        return dataSet?.size ?: 0
    }

    override fun onBindViewHolder(p0: SearchViewHolder, p1: Int) {
        val item = dataSet?.get(p1)
        p0.binding.document = item
        p0.binding.root.setOnClickListener {
            it.context.startActivity(Intent(it.context, DetailActivity::class.java).apply {
                putExtra(Constants.KEY_DOCUMENT, item)
            })
        }
        if(PreferenceUtil.isVisited(p0.binding.root.context, item!!.url!!)) {
            p0.binding.root.setBackgroundColor(Color.LTGRAY)
        } else {
            p0.binding.root.setBackgroundColor(Color.WHITE)
        }
    }

    private fun filterChanged() {
        this.dataSet = this._dataSet.filter {
            when (filter) {
                1 -> !TextUtils.isEmpty(it?.blogname)
                2 -> !TextUtils.isEmpty(it?.cafename)
                else -> true
            }
        }.sortedBy {
            if(order == 0)
                it?.title
            else
                it?.datetime
        }
        notifyDataSetChanged()
    }

    fun setSearchDataSet(data: List<Document?>?) {
        if(data != null) {
            this._dataSet.addAll(data)
        } else {
            this._dataSet.clear()
        }
        filterChanged()
    }
}