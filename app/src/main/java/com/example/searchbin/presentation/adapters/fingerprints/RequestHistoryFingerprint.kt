package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.data.entities.CachedBinInfoDTO
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.databinding.ItemRequestHistoryFragmentBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint

class RequestHistoryFingerprint(
    private val onClickItem: (CachedBinInfoDTO) -> Unit
) :
    BinItemFingerprint<ItemRequestHistoryFragmentBinding, CachedBinInfoDTO> {
    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is CachedBinInfoDTO

    override fun getLayoutId(): Int = R.layout.item_request_history_fragment

    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<ItemRequestHistoryFragmentBinding, CachedBinInfoDTO> {
        val binding = ItemRequestHistoryFragmentBinding.inflate(layoutInflater, parent, false)
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
    binding: ItemRequestHistoryFragmentBinding,
    val onClickItem: (CachedBinInfoDTO) -> Unit
) : BaseViewHolder<ItemRequestHistoryFragmentBinding, CachedBinInfoDTO>(binding) {

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
                body = item.createdAt.toString() // TODO implement dataTime object into DB
            }

        }
    }
}