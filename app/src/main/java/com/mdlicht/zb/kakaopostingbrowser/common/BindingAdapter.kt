package com.mdlicht.zb.kakaopostingbrowser.common

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.adapter.SearchRvAdapter
import com.mdlicht.zb.kakaopostingbrowser.model.Document
import com.mdlicht.zb.kakaopostingbrowser.util.TimeUtil

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("searchDataSet")
    fun setSearchDataSet(rv: RecyclerView, dataSet: List<Document?>?) {
        (rv.adapter as SearchRvAdapter).setSearchDataSet(dataSet)
    }

    @JvmStatic
    @BindingAdapter("emptyGuide")
    fun setEmptyGuideVisibility(tv: TextView, dataSet: List<Document?>?) {
        tv.visibility = if(dataSet == null || dataSet.isEmpty()) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("order")
    fun setOrder(rv: RecyclerView, order: Int = 0) {
        (rv.adapter as SearchRvAdapter).order = order
    }

    @JvmStatic
    @BindingAdapter("filter")
    fun setFilter(rv: RecyclerView, filter: Int = 0) {
        (rv.adapter as SearchRvAdapter).filter = filter
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, url: String) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))
            .thumbnail(0.1f)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("dateTime")
    fun setDateTime(tv: TextView, dateTime: String?) {
        var converted: String? = null

        dateTime?.let {
            val diff = TimeUtil.getDateDiff(it, TimeUtil.YYYY_MM_DD_HH_mm_ss, TimeUtil.YYYY_MM_DD)
            converted = when (diff) {
                0 -> tv.context.getString(R.string.common_today)
                1 -> tv.context.getString(R.string.common_yesterday)
                else -> TimeUtil.convertDateFormat(it, TimeUtil.YYYY_MM_DD_HH_mm_ss, TimeUtil.YYYY_MM_DD)
            }
        }

        tv.text = converted
    }

    @JvmStatic
    @BindingAdapter("dateTimeForDetail")
    fun setDateTimeForDetail(tv: TextView, dateTime: String?) {
        var converted: String? = null

        dateTime?.let {
            converted = TimeUtil.convertDateFormat(it, TimeUtil.YYYY_MM_DD_HH_mm_ss, TimeUtil.YYYY_MM_DD_a_hh_mm)
        }

        tv.text = converted
    }

    @JvmStatic
    @BindingAdapter("fromHtml")
    fun setTextFromHtml(tv: TextView, text: String?) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.N) {
            // noinspection deprecation
            tv.text = Html.fromHtml(text)
        } else {
            tv.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        }
    }

    @JvmStatic
    @BindingAdapter("url")
    fun setUrl(wv: WebView, url: String?) {
        wv.loadUrl(url)
    }
}