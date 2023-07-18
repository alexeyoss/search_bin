package com.example.searchbin.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.searchbin.data.network.models.BinItem

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
        return fingerprints.find { itemFingerprint -> itemFingerprint.getLayoutId() == viewType }
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
