package com.example.searchbin.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.searchbin.data.BinItem


class FingerprintDiffUtil(
    private val fingerprints: List<BinItemFingerprint<*, *>>,
) : DiffUtil.ItemCallback<BinItem>() {

    override fun areItemsTheSame(oldItem: BinItem, newItem: BinItem): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: BinItem, newItem: BinItem): Boolean {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: BinItem, newItem: BinItem): Any? {
        if (oldItem::class != newItem::class) return false

        return getItemCallback(oldItem).getChangePayload(oldItem, newItem)
    }

    private fun getItemCallback(
        binItem: BinItem
    ): DiffUtil.ItemCallback<BinItem> = fingerprints.find { it.isRelativeItem(binItem) }
        ?.getDiffUtil()
        ?.let { it as DiffUtil.ItemCallback<BinItem> }
        ?: throw IllegalStateException("DiffUtil not found for $binItem")

}