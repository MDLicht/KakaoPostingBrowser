package com.mdlicht.zb.kakaopostingbrowser.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mdlicht.zb.kakaopostingbrowser.R
import com.mdlicht.zb.kakaopostingbrowser.adapter.SearchRvAdapter
import com.mdlicht.zb.kakaopostingbrowser.common.EndlessRecyclerViewScrollListener
import com.mdlicht.zb.kakaopostingbrowser.common.showToast
import com.mdlicht.zb.kakaopostingbrowser.databinding.ActivityMainBinding
import com.mdlicht.zb.kakaopostingbrowser.dialog.OrderDialog
import com.mdlicht.zb.kakaopostingbrowser.util.PreferenceUtil
import com.mdlicht.zb.kakaopostingbrowser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), OrderDialog.OnOrderListener {
    lateinit var binding: ActivityMainBinding

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            vm = ViewModelProviders.of(this@MainActivity)[MainViewModel::class.java]
            vm.apply {
                this!!.clickOrder.observe(this@MainActivity, Observer {
                    OrderDialog.newInstance().show(supportFragmentManager, null)
                })
                this.clickSearch.observe(this@MainActivity, Observer {
                    if (TextUtils.isEmpty(it)) {
                        showToast(getString(R.string.msg_empty_keyword_guide))
                    } else {
                        (etKeyword.adapter as ArrayAdapter<String>).apply {
                            if (getPosition(it) < 0) {
                                add(it)
                                notifyDataSetChanged()
                            }
                        }
                    }
                })
            }
            rvList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = SearchRvAdapter()
                addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                        vm?.load(vm?.keyword?.get() ?: "", page)
                    }
                })
            }
            etKeyword.apply {
                setAdapter(
                    ArrayAdapter<String>(
                        this@MainActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        PreferenceUtil.getSearchHistory(this@MainActivity).toList()
                    )
                )
                setOnEditorActionListener { view, id, _ ->
                    if(id == EditorInfo.IME_ACTION_SEARCH) {
                        vm?.onSearchClick(view, view.text.toString())
                    }
                    true
                }
                setOnItemClickListener { adapterView, view, i, l ->
                    vm?.onSearchClick(view, adapterView.adapter.getItem(i) as String)
                }
                threshold = 1
            }
            spCategory.apply {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        vm?.filter?.set(p2)
                    }
                }
            }
            lifecycleOwner = this@MainActivity
        }
    }

    override fun onCancelClick(dialogFragment: DialogFragment) {
        dialogFragment.dismissAllowingStateLoss()
    }

    override fun onConfirmClick(dialogFragment: DialogFragment, order: Int) {
        binding.vm!!.order.set(order)
        dialogFragment.dismissAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        binding.rvList.adapter?.notifyDataSetChanged()
    }
}
