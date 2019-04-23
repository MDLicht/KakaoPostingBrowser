package com.mdlicht.zb.kakaopostingbrowser.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.common.Constants
import com.mdlicht.zb.kakaopostingbrowser.databinding.ActivityDetailBinding
import com.mdlicht.zb.kakaopostingbrowser.model.Document
import com.mdlicht.zb.kakaopostingbrowser.util.PreferenceUtil
import com.mdlicht.zb.kakaopostingbrowser.viewmodel.ToolbarViewModel
import com.mdlicht.zb.kakaopostingbrowser.viewmodel.factory.ToolbarViewModelFactory

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.apply {
            val documentData: Document = intent.getParcelableExtra(Constants.KEY_DOCUMENT)
            val nameValue = if(TextUtils.isEmpty(documentData.blogname)) "Cafe" else "Blog"
            name = nameValue
            toolbarWrapper.vm = ViewModelProviders.of(this@DetailActivity, ToolbarViewModelFactory(nameValue))[ToolbarViewModel::class.java]
            toolbarWrapper.vm.apply {
                this!!.clickBack.observe(this@DetailActivity, Observer {
                    finish()
                })
            }
            document = documentData
            activity = this@DetailActivity
            lifecycleOwner = this@DetailActivity
        }
    }

    fun onMoveClick(document: Document) {
        PreferenceUtil.addVisitedHistory(this, document.url!!)
        startActivity(Intent(this, WebViewActivity::class.java).apply {
            putExtra(Constants.KEY_DOCUMENT, document)
        })
    }
}
