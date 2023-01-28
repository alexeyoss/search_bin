package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.data.models.BankDTO
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.databinding.ItemEnterFragmentBankBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint
import com.example.searchbin.presentation.enter_bin_fragment.EnterBinItemClick

class BankFingerprint(
    private val onClickItem: (EnterBinItemClick) -> Unit
) : BinItemFingerprint<ItemEnterFragmentBankBinding, BankDTO> {

    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is BankDTO

    override fun getLayoutId(): Int = R.layout.item_enter_fragment_bank

    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<ItemEnterFragmentBankBinding, BankDTO> {
        val binding = ItemEnterFragmentBankBinding.inflate(layoutInflater, parent, false)
        return BankViewHolder(binding, onClickItem)
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
    binding: ItemEnterFragmentBankBinding,
    val onClickItem: (EnterBinItemClick) -> Unit
) : BaseViewHolder<ItemEnterFragmentBankBinding, BankDTO>(binding) {


    init {
        with(binding) {
            bankUrl.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                val urlText = bankUrl.text.toString().replace("www.", "https://")

                onClickItem(EnterBinItemClick.URL(urlText))
            }

            bankPhone.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                val bankPhoneFormattedText = formatPhoneNumber(bankPhone.text.toString())

                onClickItem(EnterBinItemClick.PHONE("tel:$bankPhoneFormattedText"))
            }
        }
    }

    private fun formatPhoneNumber(phoneNumber: String): String {
        // TODO choose phone number (AlertDialog etc.)
        return phoneNumber
            .split("OR")[0]
            .replace(" ", "")
            .filter { it.isDigit() }
    }

    override fun onBind(item: BankDTO) {
        super.onBind(item)

        with(binding) {
            bankName.body = item.name ?: "-"
            bankCity.body = item.city ?: "-"
            bankUrl.text = item.url ?: "-"
            bankPhone.text = item.phone ?: "-"
        }
    }
}