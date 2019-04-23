package com.mdlicht.zb.kakaopostingbrowser.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField

class ToolbarViewModel(name: String): BaseViewModel() {
    val clickBack: MutableLiveData<Boolean> = MutableLiveData()
    val name: ObservableField<String> = ObservableField(name)

    fun onBackClick() {
        clickBack.value = true
    }
}