package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.searchbin.R
import com.example.searchbin.data.models.BankDTO
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.databinding.ItemEnterFragmentBankBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint

class BankFingerprint : BinItemFingerprint<ItemEnterFragmentBankBinding, BankDTO> {

    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is BankDTO

    override fun getLayoutId(): Int = R.layout.item_enter_fragment_bank

    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<ItemEnterFragmentBankBinding, BankDTO> {
        val binding = ItemEnterFragmentBankBinding.inflate(layoutInflater, parent, false)
        return BankViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<BankDTO> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<BankDTO>() {
        override fun areItemsTheSame(oldItem: BankDTO, newItem: BankDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BankDTO, newItem: BankDTO): Boolean {
            return oldItem == newItem
        }
    }

}

class BankViewHolder(
    binding: ItemEnterFragmentBankBinding
) : BaseViewHolder<ItemEnterFragmentBankBinding, BankDTO>(binding) {

    override fun onBind(item: BankDTO) {
        super.onBind(item)

        with(binding) {
            bankName.body = item.name.toString()
            bankCity.body = item.city.toString()
            bankUrl.text = item.url
            bankPhone.text = item.phone
        }
    }
}