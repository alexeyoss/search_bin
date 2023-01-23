package com.example.searchbin.presentation.enter_bin_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbin.R
import com.example.searchbin.data.BinItem
import com.example.searchbin.databinding.FragmentEnterBinBinding
import com.example.searchbin.presentation.fingerprints.BankFingerprint
import com.example.searchbin.presentation.fingerprints.CountryFingerprint
import com.example.searchbin.presentation.fingerprints.NumberFingerprint
import com.example.searchbin.presentation.fingerprints.OtherInfoFingerprint
import com.example.searchbin.presentation.utils.CommonStates
import com.example.searchbin.presentation.utils.UiUtil
import com.example.searchbin.utils.collectOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterBinFragment : Fragment(R.layout.fragment_enter_bin) {

    private val viewModel by viewModels<EnterBinViewModelImpl>()
    private var binding: FragmentEnterBinBinding? = null

    private val fingerprintList by lazy {
        listOf(
            BankFingerprint(), CountryFingerprint(), NumberFingerprint(), OtherInfoFingerprint()
        )
    }

    private val enterBinAdapter by lazy {
        EnterBinAdapter(fingerprintList)
    }

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
        val binding = FragmentEnterBinBinding.inflate(layoutInflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initListeners()
    }

    private fun initListeners() {
        val binding = checkNotNull(binding)

        viewModel.binInfoFlow.collectOnLifecycle(this@EnterBinFragment) { state ->
            when (state) {
                is CommonStates.Success -> {
                    binding.progressBar.visibility = View.GONE
                    enterBinAdapter.submitList(state.data as List<BinItem>)
                }
                is CommonStates.Empty -> Unit
                is CommonStates.Error -> Snackbar.make(
                    binding.root, "Shit happens!", Snackbar.LENGTH_SHORT
                ).show()

                is CommonStates.Loading -> {
                    // TODO refactoring to side effect approach
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }


        with(binding) {
            binInputLayout.setEndIconOnClickListener {
                viewModel.getBinItems(binding.binInputEditText.text.toString())
            }
        }
    }

    private fun initRecyclerView() {
        val binding = checkNotNull(binding)
        with(binding) {
            rvBinInfo.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = enterBinAdapter
                itemAnimator = null
                addOnScrollListener(onScrollListener)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        this.binding = null
    }
}