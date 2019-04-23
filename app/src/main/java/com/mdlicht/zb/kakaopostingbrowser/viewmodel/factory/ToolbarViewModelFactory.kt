package com.mdlicht.zb.kakaopostingbrowser.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mdlicht.zb.kakaopostingbrowser.viewmodel.ToolbarViewModel

class ToolbarViewModelFactory(val name: String): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToolbarViewModel(name) as T
    }
}