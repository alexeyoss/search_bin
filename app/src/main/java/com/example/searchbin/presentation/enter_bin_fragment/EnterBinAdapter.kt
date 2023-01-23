package com.example.searchbin.presentation.enter_bin_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.searchbin.data.BinItem
import com.example.searchbin.presentation.utils.BaseViewHolder
import com.example.searchbin.presentation.utils.BinItemFingerprint
import com.example.searchbin.presentation.utils.FingerprintDiffUtil

class EnterBinAdapter(
    private val fingerprints: List<BinItemFingerprint<*, *>>
) : ListAdapter<BinItem, BaseViewHolder<ViewBinding, BinItem>>(
    FingerprintDiffUtil(fingerprints)
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, BinItem> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getLayoutId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as BaseViewHolder<ViewBinding, BinItem> }
            ?: throw IllegalArgumentException("View type not found: $viewType")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, BinItem>, position: Int) {
        holder.onBind(currentList[position])
    }


    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding, BinItem>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(currentList[position], payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return fingerprints.find { it.isRelativeItem(item) }
            ?.getLayoutId()
            ?: throw IllegalArgumentException("View type not found: $item")
    }
}
