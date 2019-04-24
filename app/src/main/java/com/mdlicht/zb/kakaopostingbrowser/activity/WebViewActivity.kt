package com.mdlicht.zb.kakaopostingbrowser.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebViewClient
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.common.Constants
import com.mdlicht.zb.kakaopostingbrowser.databinding.ActivityWebViewBinding
import com.mdlicht.zb.kakaopostingbrowser.model.Document
import com.mdlicht.zb.kakaopostingbrowser.viewmodel.ToolbarViewModel
import com.mdlicht.zb.kakaopostingbrowser.viewmodel.factory.ToolbarViewModelFactory

class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view)
        binding.apply {
            val documentData = intent.getParcelableExtra<Document>(Constants.KEY_DOCUMENT)
            val nameValue = if(TextUtils.isEmpty(documentData.blogname))  getString(R.string.filter_cafe) else getString(R.string.filter_blog)
            document = documentData
            toolbarWrapper.vm = ViewModelProviders.of(this@WebViewActivity, ToolbarViewModelFactory(nameValue))[ToolbarViewModel::class.java]
            toolbarWrapper.vm.apply {
                this!!.clickBack.observe(this@WebViewActivity, Observer {
                    finish()
                })
            }
            wvWeb.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
            }
            url = documentData.url
            lifecycleOwner = this@WebViewActivity
        }
    }
}
