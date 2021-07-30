package com.hwei.lib_base.paging

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.hwei.lib_base.adapter.IMultiBean

/**
 * 支持多布局的pagingAdapter
 */
abstract class BaseMultiPagingAdapter<T : Any>(callback: DiffUtil.ItemCallback<T>) :
    BasePagingAdapter<ViewDataBinding, T>(callback) {

    private val multiItemMap = mutableMapOf<Int, Int>()

    final override fun setLayoutId(): Int {
        return View.NO_ID
    }

    final override fun isSupportMulti(): Boolean {
        return true
    }

    override fun getItemMultiViewType(position: Int): Int {
        getItem(position).apply {
            if (this is IMultiBean) {
                return this.viewType
            }
        }
        throw NullPointerException("check your bean has implements IMultiBean?")
    }

    override fun onCreateMultiViewBinding(parent: ViewGroup, viewType: Int): Int {
        multiItemMap.keys.find {
            it == viewType
        }?.let {
            return multiItemMap[it]!!
        }
        throw NullPointerException("check MultiItem has add all?")
    }

    /**
     * first : viewType
     * second : layoutId
     */
    fun addMultiItem(pair: Pair<Int, Int>) {
        multiItemMap[pair.first] = pair.second
    }
}