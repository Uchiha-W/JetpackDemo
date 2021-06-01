package com.hwei.lib_common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.hwei.lib_common.base.BaseViewHolder

/**
 * 支持多布局Adapter
 *
 * data class SystemBean:IMultiBean{
 *}
 *
 * SystemAdapter : BaseMultiAdapter<SystemBean>{
 *      init {
 *           addMultiItem(MultiItem(1, R.layout.item_))
 *           addMultiItem(MultiItem(2, R.layout.item_image))
 *           }
 *
 *      override fun onBindExtendsViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
 *           when (currentList[position].viewType) {
 *           1 -> holder.binding.setVariable(BR.title, currentList[position].content)
 *           2 -> holder.binding.setVariable(BR.drawableRes, R.drawable.ic_system)
 *           }
 *       }
 *   }
 *
 */
abstract class BaseMultiAdapter<T : Any>(itemCallback: DiffUtil.ItemCallback<T>) :
    BaseAdapter<ViewDataBinding, T>(itemCallback) {

    private var list = mutableListOf<MultiItem>()

    final override fun setLayoutId(): Int {
        return View.NO_ID
    }

    override fun isSupportMulti(): Boolean {
        return true
    }

    final override fun onCreateMultiViewBinding(
        parent: ViewGroup,
        viewType: Int
    ): ViewDataBinding? {
        list.find { viewType == it.viewType }?.apply {
            return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        }
        throw NullPointerException("check MultiItem has add all?")
    }

    final override fun onBindExtendsViewHolder(
        holder: BaseViewHolder<ViewDataBinding>,
        position: Int
    ) {

    }

    override fun onBindMultiViewHolder(
        holder: BaseViewHolder<*>,
        position: Int
    ) {
        onBindExtendMultiViewHolder(holder, position)
    }

    abstract fun onBindExtendMultiViewHolder(headerViewHolder: BaseViewHolder<*>, position: Int)

    final override fun getItemMultiViewType(position: Int): Int {
        currentList[position].apply {
            if (this is IMultiBean) {
                return this.viewType
            }
        }
        return -1
    }

    fun addMultiItem(multiItem: MultiItem) {
        list.add(multiItem)
    }
}