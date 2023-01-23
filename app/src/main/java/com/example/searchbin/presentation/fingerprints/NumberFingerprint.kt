package com.example.searchbin.presentation.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.searchbin.R
import com.example.searchbin.data.BinItem
import com.example.searchbin.data.models.NumberDTO
import com.example.searchbin.databinding.ItemEnterBinNumberBinding
import com.example.searchbin.presentation.utils.BaseViewHolder
import com.example.searchbin.presentation.utils.BinItemFingerprint

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
            numberLength.body = item.length.toString()
            numberLuhn.body = item.luhn?.publishStringPresenter() ?: "null"
        }
    }

    private fun Boolean.publishStringPresenter(): String {
        return if (this) "Yes" else "No"
    }
}