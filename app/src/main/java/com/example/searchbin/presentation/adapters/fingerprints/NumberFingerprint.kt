package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.searchbin.R
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.data.models.NumberDTO
import com.example.searchbin.databinding.ItemEnterBinNumberBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint

class NumberFingerprint : BinItemFingerprint<ItemEnterBinNumberBinding, NumberDTO> {

    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is NumberDTO

    override fun getLayoutId(): Int = R.layout.item_enter_bin_number

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemEnterBinNumberBinding, NumberDTO> {
        val binding = ItemEnterBinNumberBinding.inflate(layoutInflater, parent, false)
        return NumberViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<NumberDTO> = diffUtil


    private val diffUtil = object : DiffUtil.ItemCallback<NumberDTO>() {
        override fun areItemsTheSame(oldItem: NumberDTO, newItem: NumberDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NumberDTO, newItem: NumberDTO): Boolean {
            return oldItem == newItem
        }

    }
}

class NumberViewHolder(
    binding: ItemEnterBinNumberBinding
) : BaseViewHolder<ItemEnterBinNumberBinding, NumberDTO>(binding) {

    override fun onBind(item: NumberDTO) {
        super.onBind(item)
        with(binding) {
            numberLength.body = item.length?.toString() ?: "-"
            numberLuhn.body = item.luhn?.extractYesNoAnswer() ?: "-"
        }
    }

    private fun Boolean.extractYesNoAnswer(): String {
        return if (this) "Yes" else "No"
    }
}