package com.example.restaurantws.ui.pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.models.pedidos.Pedido
import com.example.restaurantws.databinding.FragmentPedidosBinding
import com.example.restaurantws.ui.pedidos.adapter.PedidosAdapter
import com.example.restaurantws.ui.pedidos.adapter.PedidosAdapterCallback
import com.example.restaurantws.ui.splash.MainViewModel
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val pedidosViewModel: PedidosViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

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
        setUpHeader()
        observeCategories()
        pedidosViewModel.loadPedidos()

    }

    private fun setUpHeader() {
        binding.pedidosHeader.apply {
            titleHeader.text = "Tus pedidos"
            headerDesc.text = "Mira tu historial de pedidos"
        }
    }

    private fun setUpRcView() {
        rcViewAdapter = PedidosAdapter(mutableListOf(), rcViewAdapterCallback())

        binding.rcViewPedidos.apply {
            adapter = rcViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun rcViewAdapterCallback(): PedidosAdapterCallback {
        return object : PedidosAdapterCallback {
            override fun sendPedido(pedido: Pedido) {
                mainViewModel.enviarPedido(pedido)

                lifecycleScope.launchWhenStarted {
                    mainViewModel.enviarPedidoResult.collect {
                        when (it) {
                            is Resource.Error -> with(binding) {
                                progressBarPedidos.isVisible = false
                                toast(it.error?.message)
                            }
                            is Resource.Loading -> with(binding) {
                                progressBarPedidos.isVisible = true
                            }
                            is Resource.Success -> {
                                pedidosViewModel.loadPedidos()
                            }
                            else -> {
                            }
                        }
                    }
                }

            }

            override fun deletePedido(pedido: Pedido, position: Int) {
                mainViewModel.deletePedido(pedido)

                lifecycleScope.launchWhenStarted {
                    mainViewModel.deletePedidoResult.collect {
                        when (it) {
                            is Resource.Error -> toast(it.error?.message)
                            is Resource.Success -> {
                                rcViewAdapter.deleteItem(pedido, position)
                            }
                            else -> {
                            }
                        }
                    }
                }

            }

            override fun onClickPedido(pedido: Pedido) {
                val title = if (pedido.isLocal) "Pedido en proceso" else "Pedido #${pedido.id}"
                val action =
                    PedidosFragmentDirections.actionPedidosFragmentToPedidoDetailFragment(
                        pedido,
                        title
                    )
                findNavController().navigate(action)
            }
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