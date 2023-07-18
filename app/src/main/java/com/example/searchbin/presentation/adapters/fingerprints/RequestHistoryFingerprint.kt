package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.data.network.models.BinItem
import com.example.searchbin.databinding.ItemRequestHistoryBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint

class RequestHistoryFingerprint(
    private val onClickItem: (CachedBinInfoDTO) -> Unit
) : BinItemFingerprint<ItemRequestHistoryBinding, CachedBinInfoDTO> {
    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is CachedBinInfoDTO

    override fun getLayoutId(): Int = R.layout.item_request_history

    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<ItemRequestHistoryBinding, CachedBinInfoDTO> {
        val binding = ItemRequestHistoryBinding.inflate(layoutInflater, parent, false)
        return CachedBinInfoViewHolder(binding, onClickItem)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<CachedBinInfoDTO> = diffUtils

    private val diffUtils = object : DiffUtil.ItemCallback<CachedBinInfoDTO>() {
        override fun areItemsTheSame(
            oldItem: CachedBinInfoDTO, newItem: CachedBinInfoDTO
        ): Boolean {
            return oldItem.bin == newItem.bin
        }

        override fun areContentsTheSame(
            oldItem: CachedBinInfoDTO, newItem: CachedBinInfoDTO
        ): Boolean {
            return oldItem == newItem
        }
    }
}

class CachedBinInfoViewHolder(
    binding: ItemRequestHistoryBinding,
    val onClickItem: (CachedBinInfoDTO) -> Unit
) : BaseViewHolder<ItemRequestHistoryBinding, CachedBinInfoDTO>(binding) {

    init {
        binding.infoBtn.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onClickItem(item)
        }
    }

    override fun onBind(item: CachedBinInfoDTO) {
        super.onBind(item)

        with(binding) {
            historyItem.apply {
                title = item.bin.toString()
                body = item.createdAt ?: "-"
            }

        }
    }
}