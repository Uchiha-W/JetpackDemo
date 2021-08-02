package com.hwei.lib_base.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseFragment : Fragment() {

    lateinit var mContext: Context
    private var showingLoading = AtomicInteger(0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        initView()
        initData()
        setEvent()
        return layoutInflater.inflate(setLayoutId(), container, false)
    }

    @LayoutRes
    abstract fun setLayoutId(): Int

    abstract fun initView()

    abstract fun initData()

    abstract fun setEvent()

    protected fun observeLoading(vm: BaseViewModel) {
        vm.let { it ->
            it.showLoadingLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    startLoading()
                } else {
                    stopLoading()
                }
            }
        }
    }

    private fun startLoading() {
        if (showingLoading.incrementAndGet() == 1) {
            //todo: show a loading dialog
        }
    }

    private fun stopLoading() {
        if (showingLoading.decrementAndGet() == 0) {
            //todo: hide a loading dialog
        }
    }
}