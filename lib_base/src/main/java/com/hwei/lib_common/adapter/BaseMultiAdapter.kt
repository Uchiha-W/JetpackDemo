package com.hwei.lib_common.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil

/**
 * 支持多布局Adapter
 *
 * data class SystemBean:IMultiBean{
 *}
 *
 * SystemAdapter : BaseMultiAdapter<SystemBean>{
 *      init {
 *           addMultiItem(1 to R.layout.item_)
 *           addMultiItem(2 to R.layout.item_image)
 *           }
 *
 *     override fun onBindExtendsViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
            when (currentList[position].viewType) {
                1 -> holder.binding.setVariable(BR.title, currentList[position].content)
                2 -> holder.binding.setVariable(BR.drawableRes, R.drawable.ic_system)
                }
             }
 *   }
 *
 */
abstract class BaseMultiAdapter<T : Any>(itemCallback: DiffUtil.ItemCallback<T>) :
    BaseAdapter<ViewDataBinding, T>(itemCallback) {

    private val multiItemMap = mutableMapOf<Int, Int>()

    final override fun setLayoutId(): Int {
        return View.NO_ID
    }

    final override fun isSupportMulti(): Boolean {
        return true
    }

    final override fun onCreateMultiViewBinding(
        parent: ViewGroup,
        viewType: Int
    ): Int {
        multiItemMap.keys.find {
            it == viewType
        }?.let {
            return multiItemMap[it]!!
        }
        throw NullPointerException("check MultiItem has add all?")
    }


    final override fun getItemMultiViewType(position: Int): Int {
        currentList[position].apply {
            if (this is IMultiBean) {
                return this.viewType
            }
        }
        throw NullPointerException("check your bean has implements IMultiBean?")
    }

    /**
     * first : viewType
     * second : layoutId
     */
    fun addMultiItem(pair: Pair<Int, Int>) {
        multiItemMap[pair.first] = pair.second
    }
}