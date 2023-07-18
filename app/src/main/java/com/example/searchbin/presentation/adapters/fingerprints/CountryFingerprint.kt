package com.example.searchbin.presentation.adapters.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.data.network.models.BinItem
import com.example.searchbin.data.network.models.CountryDTO
import com.example.searchbin.databinding.ItemEnterBinCountryBinding
import com.example.searchbin.presentation.adapters.BaseViewHolder
import com.example.searchbin.presentation.adapters.BinItemFingerprint
import com.example.searchbin.presentation.enter_bin_fragment.EnterBinItemClick

class CountryFingerprint(
    private val onClickItem: (EnterBinItemClick) -> Unit
) : BinItemFingerprint<ItemEnterBinCountryBinding, CountryDTO> {

    override fun isRelativeItem(binItem: BinItem): Boolean = binItem is CountryDTO

    override fun getLayoutId(): Int = R.layout.item_enter_bin_country

    override fun getViewHolder(
        layoutInflater: LayoutInflater, parent: ViewGroup
    ): BaseViewHolder<ItemEnterBinCountryBinding, CountryDTO> {
        val binding = ItemEnterBinCountryBinding.inflate(layoutInflater, parent, false)
        return CountryViewHolder(binding, onClickItem)
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
    binding: ItemEnterBinCountryBinding,
    private val onClickItem: (EnterBinItemClick) -> Unit
) : BaseViewHolder<ItemEnterBinCountryBinding, CountryDTO>(binding) {

    override fun onBind(item: CountryDTO) {
        super.onBind(item)
        with(binding) {
            countryName.body = item.name ?: "-"

            countryCoordination.text = String.format(
                "(latitude: %1\$s , longitude: %2\$s)",
                item.latitude ?: "-",
                item.longitude ?: "-"
            )
            countryCurrency.body = item.currency ?: "-"
            countryNumeric.body = item.numeric ?: "-"
            countryAlpha2.body = item.alpha2 ?: "-"
        }

        /** TODO find better way to bind listener, cause if we will use multiply [CountryFingerprint]
         * views in one recycleview, [onBind] method will triggered by every scroll action*/
        binding.countryCoordination.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onClickItem(EnterBinItemClick.LOCATION("geo:${item.latitude},${item.longitude}"))
        }
    }
}