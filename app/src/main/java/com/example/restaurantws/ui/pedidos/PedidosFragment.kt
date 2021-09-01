package com.example.restaurantws.ui.pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantws.core.Resource
import com.example.restaurantws.databinding.FragmentPedidosBinding
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val pedidosViewModel: PedidosViewModel by viewModel()

    private lateinit var rcViewAdapter: PedidosAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRcView()
        observeCategories()
        pedidosViewModel.loadProducts()

    }

    private fun setUpRcView() {
        rcViewAdapter = PedidosAdapter(listOf())

        binding.rcViewPedidos.apply {
            adapter = rcViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeCategories() {
        lifecycleScope.launchWhenStarted {
            pedidosViewModel.signUpResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarPedidos.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarPedidos.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBarPedidos.isVisible = false
                        rcViewAdapter.setItems(it.data ?: listOf())
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}