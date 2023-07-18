package com.example.searchbin.presentation.request_history_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchbin.R
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.databinding.RequestHistoryLayoutBinding
import com.example.searchbin.presentation.adapters.EnterBinAdapter
import com.example.searchbin.presentation.adapters.fingerprints.RequestHistoryFingerprint
import com.example.searchbin.presentation.utils.CommonSideEffects
import com.example.searchbin.presentation.utils.CommonUiStates
import com.example.searchbin.utils.collectOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestHistoryFragment : Fragment(R.layout.request_history_layout) {


    private var binding: RequestHistoryLayoutBinding? = null
    private val viewModel by viewModels<RequestHistoryViewModelImpl>()

    private var lastBottomSheetData: CachedBinInfoDTO? = null

    private val fingerprintList by lazy {
        listOf(
            RequestHistoryFingerprint(::onClickInfoButton)
        )
    }

    private val historyAdapter by lazy {
        EnterBinAdapter(fingerprintList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = RequestHistoryLayoutBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            initListeners()
            initRecyclerView()
            viewModel.setEvent(RequestHistoryEvents.GetRequestHistory)

            /** Deprecated approach is used cause API 33 not so widespread from users*/
            savedInstanceState.getParcelable<CachedBinInfoDTO>(RESTORE_KEY)?.also {
//                onClickInfoButton(it)
            }
        } else {
            initRecyclerView()
            initListeners()
            viewModel.setEvent(RequestHistoryEvents.GetRequestHistory)
        }

    }

    private fun initListeners() {
        val binding = checkNotNull(binding)

        viewModel.uiStateFlow.collectOnLifecycle(this@RequestHistoryFragment) { state ->
            when (state) {
                is CommonUiStates.Error -> Unit
                is CommonUiStates.Initial -> Unit
                is CommonUiStates.Loading -> Unit
                is CommonUiStates.Success -> historyAdapter.submitList(state.data)
                is CommonUiStates.SuccessNoResult -> historyAdapter.submitList(emptyList())
            }
        }


        viewModel.sideEffectsFlow.collectOnLifecycle(this@RequestHistoryFragment) { sideEffect ->
            when (sideEffect) {
                is CommonSideEffects.Initial -> Unit
                is CommonSideEffects.Loading -> Unit
                is CommonSideEffects.NoSearchResult -> {
                    Snackbar.make(
                        binding.root, getString(R.string.noResult_snackbar_text), Snackbar.LENGTH_SHORT
                    ).show()
                }

                is CommonSideEffects.ShowError -> {
                    Snackbar.make(
                        binding.root, getString(R.string.error_snackbar_text), Snackbar.LENGTH_SHORT
                    ).show()
                }

                is CommonSideEffects.ShowResult -> Unit
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigate(RequestHistoryFragmentDirections.actionRequestHistoryFragmentToEnterBinFragment())
        }
    }

    private fun onClickInfoButton(cachedBinInfoDTO: CachedBinInfoDTO) {
        lastBottomSheetData = cachedBinInfoDTO

        findNavController().navigate(
            RequestHistoryFragmentDirections.actionRequestHistoryFragmentToRequestHistoryDetailsBottomSheet(
                cachedBinInfoDTO
            )
        )
    }

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)
        with(binding.recycleView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
            itemAnimator = null

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (lastBottomSheetData != null) {
            outState.putParcelable(RESTORE_KEY, lastBottomSheetData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val binding = checkNotNull(binding)

        binding.recycleView.adapter = null
        binding.backBtn.setOnClickListener(null)
        this.binding = null
    }

    companion object {
        @JvmStatic
        val RESTORE_KEY = "RESTORE_KEY"
    }
}