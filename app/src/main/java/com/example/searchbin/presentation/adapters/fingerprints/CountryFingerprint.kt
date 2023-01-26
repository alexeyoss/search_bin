package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.searchbin.R
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.data.models.CountryDTO
import com.example.searchbin.databinding.ItemEnterBinCountryBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint

class CountryFingerprint : BinItemFingerprint<ItemEnterBinCountryBinding, CountryDTO> {

    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is CountryDTO

    override fun getLayoutId(): Int = R.layout.item_enter_bin_country

    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<ItemEnterBinCountryBinding, CountryDTO> {
        val binding = ItemEnterBinCountryBinding.inflate(layoutInflater, parent, false)
        return CountryViewHolder(binding)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<CountryDTO> = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<CountryDTO>() {
        override fun areItemsTheSame(oldItem: CountryDTO, newItem: CountryDTO): Boolean {
            return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CountryDTO, newItem: CountryDTO): Boolean {
            return oldItem == newItem
        }
    }
}

class CountryViewHolder(
    binding: ItemEnterBinCountryBinding
) : BaseViewHolder<ItemEnterBinCountryBinding, CountryDTO>(binding) {

    override fun onBind(item: CountryDTO) {
        super.onBind(item)
        with(binding) {
            countryName.body = item.name.toString()
            // TODO emoji set
            countryCoordination.text = String.format(
                "(latitude: %1\$s , longitude: %2\$s )", item.latitude, item.longitude
            )
            countryCurrency.body = item.currency.toString()
            countryNumeric.body = item.numeric.toString()
            countryAlpha2.body = item.alpha2.toString()
        }
    }
}