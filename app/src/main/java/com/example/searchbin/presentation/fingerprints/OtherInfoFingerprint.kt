package com.example.searchbin.presentation.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.searchbin.R
import com.example.searchbin.data.BinItem
import com.example.searchbin.data.models.OtherInfoDTO
import com.example.searchbin.databinding.ItemEnterBinOtherInfoBinding
import com.example.searchbin.presentation.utils.BaseViewHolder
import com.example.searchbin.presentation.utils.BinItemFingerprint

class OtherInfoFingerprint : BinItemFingerprint<ItemEnterBinOtherInfoBinding, OtherInfoDTO> {
    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is OtherInfoDTO

    override fun getLayoutId(): Int = R.layout.item_enter_bin_other_info

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemEnterBinOtherInfoBinding, OtherInfoDTO> {
        val binding = ItemEnterBinOtherInfoBinding.inflate(layoutInflater, parent, false)
        return OtherInfoViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<OtherInfoDTO> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<OtherInfoDTO>() {
        override fun areItemsTheSame(oldItem: OtherInfoDTO, newItem: OtherInfoDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OtherInfoDTO, newItem: OtherInfoDTO): Boolean {
            return oldItem == newItem
        }
    }
}

class OtherInfoViewHolder(
    binding: ItemEnterBinOtherInfoBinding
) : BaseViewHolder<ItemEnterBinOtherInfoBinding, OtherInfoDTO>(binding) {
    override fun onBind(item: OtherInfoDTO) {
        super.onBind(item)

        with(binding) {
            otherScheme.body = item.scheme.toString()
            otherType.body = item.type.toString()
            otherBrand.body = item.brand.toString()
            otherPrepaid.body = item.prepaid?.publishStringPresenter() ?: "null"
        }
    }

    private fun Boolean.publishStringPresenter(): String {
        return if (this) "Yes" else "No"
    }
}