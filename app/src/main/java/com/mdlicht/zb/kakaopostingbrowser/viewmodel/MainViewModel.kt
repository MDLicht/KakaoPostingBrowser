package com.mdlicht.zb.kakaopostingbrowser.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.mdlicht.zb.kakaopostingbrowser.api.RetrofitUtil
import com.mdlicht.zb.kakaopostingbrowser.model.Document
import com.mdlicht.zb.kakaopostingbrowser.model.Response
import com.mdlicht.zb.kakaopostingbrowser.util.PreferenceUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainViewModel : BaseViewModel() {
    val keyword: ObservableField<String> = ObservableField()
    val order: ObservableInt = ObservableInt(0)
    val filter: ObservableInt = ObservableInt(0)
    var dataSet: ObservableField<List<Document?>?> = ObservableField()

    var clickOrder: MutableLiveData<Boolean> = MutableLiveData()
    var clickSearch: MutableLiveData<String> = MutableLiveData()

    fun onSearchClick(view: View, keyword: String) {
        clickSearch.value = keyword
        dataSet.set(null)
        PreferenceUtil.addSearchHistory(view.context, keyword)
        load(keyword, 1)
    }

    fun load(keyword: String, page: Int, size: Int = 25) {
        add(
            Observable
                .zip(
                    RetrofitUtil.create().searchBlog(keyword, page, size),
                    RetrofitUtil.create().searchCafe(keyword, page, size),
                    BiFunction<Response, Response, MutableList<Document?>> {
                            t1, t2 ->
                        val result = mutableListOf<Document?>()
                        t1.documents?.let {
                            result.addAll(it)
                        }
                        t2.documents?.let {
                            result.addAll(it)
                        }
                        result
                    }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataSet.set(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun onOrderClick() {
        clickOrder.value = true
    }
}