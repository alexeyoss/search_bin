package com.example.searchbin.presentation.request_history_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.searchbin.R
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.databinding.RequestHistoryDetailsLayoutBinding
import com.example.searchbin.presentation.navigate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson

class RequestHistoryDetailsBottomSheet :
    BottomSheetDialogFragment(R.layout.request_history_details_layout) {

    private var binding: RequestHistoryDetailsLayoutBinding? = null

    private val gson by lazy(LazyThreadSafetyMode.NONE) {
        Gson().newBuilder().setPrettyPrinting().create()
    }

    private var lastData: CachedBinInfoDTO? = null

    override fun getTheme(): Int = R.style.RequestHistoryBottomSheetDialogTheme
    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = RequestHistoryDetailsLayoutBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            initListeners()
            /** Deprecated approach is used cause API 33 not so widespread from users*/
            savedInstanceState.getParcelable<CachedBinInfoDTO>(RESTORE_KEY)?.also {
                lastData = it
                initViews(it)
            }
        } else {
            initListeners()
        }
    }

    private fun initListeners() {
        val binding = checkNotNull(binding)
        with(binding) {
            navigate().listenResult(
                CachedBinInfoDTO::class.java, this@RequestHistoryDetailsBottomSheet
            ) {
                initViews(it)
                lastData = it
            }


            closeButton.setOnClickListener { dismiss() }
        }


    }

    private fun initViews(cachedBinInfoDTO: CachedBinInfoDTO) {
        val binding = checkNotNull(binding)
        with(binding) {
            requestDetail.text = gson.toJson(cachedBinInfoDTO)
            title.text = String.format(
                getString(R.string.history_bin_details_text), cachedBinInfoDTO.bin
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lastData != null) {
            outState.putParcelable(RESTORE_KEY, lastData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val binding = checkNotNull(binding)

        binding.closeButton.setOnClickListener(null)
        this.binding = null
    }

    companion object {

        @JvmStatic
        val RESTORE_KEY = "RESTORE_KEY"
    }
}