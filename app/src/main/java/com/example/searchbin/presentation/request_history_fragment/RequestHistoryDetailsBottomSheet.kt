package com.example.searchbin.presentation.request_history_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.searchbin.R
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.databinding.RequestHistoryDetailsLayoutBinding
import com.example.searchbin.presentation.navigate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson

class RequestHistoryDetailsBottomSheet :
    BottomSheetDialogFragment(R.layout.request_history_details_layout) {

    private var binding: RequestHistoryDetailsLayoutBinding? = null

    private val gson by lazy {
        Gson().newBuilder().setPrettyPrinting().create()
    }

    override fun getTheme(): Int = R.style.RequestHistoryBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = RequestHistoryDetailsLayoutBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListeners()
    }

    private fun initListeners() {
        val binding = checkNotNull(binding)
        with(binding) {
            navigate().listenResult(
                CachedBinInfoDTO::class.java,
                this@RequestHistoryDetailsBottomSheet
            ) {
                requestDetail.text = gson.toJson(it)
                title.text = String.format(
                    getString(R.string.history_bin_details_text), it.bin
                )
            }

            closeButton.setOnClickListener { dismiss() }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        val binding = checkNotNull(binding)

        binding.closeButton.setOnClickListener(null)
        this.binding = null
    }
}