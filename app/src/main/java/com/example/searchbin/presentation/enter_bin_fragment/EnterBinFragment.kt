package com.example.searchbin.presentation.enter_bin_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.databinding.EnterBinFragmentLayoutBinding
import com.example.searchbin.presentation.adapters.EnterBinAdapter
import com.example.searchbin.presentation.adapters.fingerprints.BankFingerprint
import com.example.searchbin.presentation.adapters.fingerprints.CountryFingerprint
import com.example.searchbin.presentation.adapters.fingerprints.NumberFingerprint
import com.example.searchbin.presentation.adapters.fingerprints.OtherInfoFingerprint
import com.example.searchbin.presentation.utils.CommonSideEffects
import com.example.searchbin.presentation.utils.CommonUiStates
import com.example.searchbin.presentation.utils.UiUtil
import com.example.searchbin.utils.collectOnLifecycle
import com.example.searchbin.utils.textChanges
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EnterBinFragment : Fragment(R.layout.enter_bin_fragment_layout) {

    private val viewModel by viewModels<EnterBinViewModelImpl>()
    private var binding: EnterBinFragmentLayoutBinding? = null

    private val fingerprintList by lazy {
        listOf(
            BankFingerprint(::onClickItem),
            CountryFingerprint(::onClickItem),
            NumberFingerprint(),
            OtherInfoFingerprint()
        )
    }

    private val enterBinAdapter by lazy {
        EnterBinAdapter(fingerprintList)
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                UiUtil.hideKeyBoard(recyclerView)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = EnterBinFragmentLayoutBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = checkNotNull(binding)

        if (savedInstanceState != null) {
            initRecyclerView()
            initListeners()

            savedInstanceState.getString(RESTORE_KEY)?.let { lastBin ->
                binding.binInputEditText.setText(lastBin)
                viewModel.setEvent(EnterBinFragmentEvents.GetBinInfo(lastBin))
            }
        }
        initRecyclerView()
        initListeners()

    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun initListeners() {
        val binding = checkNotNull(binding)


        viewModel.uiStateFlow.collectOnLifecycle(this@EnterBinFragment) { state ->
            when (state) {
                is CommonUiStates.Success -> enterBinAdapter.submitList(state.data)
                is CommonUiStates.SuccessNoResult -> enterBinAdapter.submitList(emptyList())
                is CommonUiStates.Initial -> Unit
                is CommonUiStates.Error -> Unit
                is CommonUiStates.Loading -> Unit
            }
        }

        with(binding) {

            binInputEditText.textChanges().debounce(INPUT_DEBOUNCE).filter {
                !it.isNullOrEmpty()
            }.map { it.toString() }.onEach { binNumber ->
                viewModel.setEvent(EnterBinFragmentEvents.GetBinInfo(binNumber))
            }.launchIn(lifecycleScope)


            viewModel.sideEffectsFlow.collectOnLifecycle(this@EnterBinFragment) { sideEffect ->
                when (sideEffect) {
                    CommonSideEffects.Initial -> Unit
                    CommonSideEffects.NoSearchResult -> {
                        isLoading(false)

                        Snackbar.make(
                            binding.root, getString(R.string.noResult_snackbar_text), Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    CommonSideEffects.ShowError -> {
                        isLoading(false)

                        Snackbar.make(
                            binding.root, getString(R.string.error_snackbar_text), Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    CommonSideEffects.Loading -> isLoading(true)
                    CommonSideEffects.ShowResult -> isLoading(false)
                }
            }

            binInputLayout.setEndIconOnClickListener {
                findNavController().navigate(EnterBinFragmentDirections.actionEnterBinFragmentToRequestHistoryFragment())
            }
        }
    }

    private fun isLoading(value: Boolean) {
        val binding = checkNotNull(binding)
        with(binding) {
            if (value) {
                progressBar.visibility = VISIBLE
                recycleView.visibility = GONE
            } else {
                progressBar.visibility = GONE
                recycleView.visibility = VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)
        with(binding) {
            recycleView.apply {
                setHasFixedSize(true)
                setRecycledViewPool(viewPool)

                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = enterBinAdapter
                itemAnimator = null
                addOnScrollListener(onScrollListener)
            }
        }
    }

    private fun onClickItem(onClickItem: EnterBinItemClick) {
        when (onClickItem) {
            is EnterBinItemClick.URL -> startImplicitIntent(
                Intent.ACTION_VIEW, onClickItem.data
            )

            is EnterBinItemClick.PHONE -> startImplicitIntent(
                Intent.ACTION_DIAL, onClickItem.data
            )

            is EnterBinItemClick.LOCATION -> startImplicitIntent(
                Intent.ACTION_VIEW, onClickItem.data
            )
        }
    }

    private fun startImplicitIntent(
        intentAction: String, intentData: String
    ) {
        val intent = Intent(intentAction).apply {
            data = Uri.parse(intentData)
        }
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        /** DEBUG cause the null value was returned */
        val binding = checkNotNull(binding)
        val currentInputText = binding.binInputEditText.text.toString()

        if (currentInputText.isNotEmpty()) {
            outState.putString(RESTORE_KEY, currentInputText)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val binding = checkNotNull(binding)

        with(binding) {
            binInputLayout.setEndIconOnClickListener(null)
            recycleView.clearOnScrollListeners()
            recycleView.adapter = null
        }

        this.binding = null
    }

    companion object {
        @JvmStatic
        val RESTORE_KEY = "RESTORE_KEY"

        @JvmStatic
        val INPUT_DEBOUNCE: Long = 400L
    }

}