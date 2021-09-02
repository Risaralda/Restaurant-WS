package com.example.restaurantws.ui.pedido_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.restaurantws.core.Resource
import com.example.restaurantws.core.api.models.ApiResponse
import com.example.restaurantws.data.main.models.pedidos.Pedido
import com.example.restaurantws.databinding.FragmentPedidoDetailBinding
import com.example.restaurantws.ui.products.ProductsViewModel
import com.example.restaurantws.ui.splash.MainViewModel
import com.example.restaurantws.utils.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PedidoDetailFragment : Fragment() {
    private var _binding: FragmentPedidoDetailBinding? = null
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val productsViewModel: ProductsViewModel by viewModel()

    private lateinit var rcViewAdapter: PedidoDetailAdapter

    private val binding get() = _binding!!

    private val navArgs by navArgs<PedidoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRcView()
        setUpCard(navArgs.pedido, navArgs.pedido.isLocal)
        observePedido()

        if (!navArgs.pedido.isLocal) {
            productsViewModel.loadAllProducts()
            observeProducts()
        }

    }

    private fun setUpCard(pedido: Pedido, isVisibleButton: Boolean) = with(binding) {
        val iva = pedido.total * .19

        "$ $iva".also { pedidoIva.text = it }
        "$ ${pedido.total + iva}".also { pedidoTotal.text = it }

        btnEnviarPedido.apply {
            isVisible = isVisibleButton
            setOnClickListener {
                sendPedido()
            }
        }

    }

    private fun observePedido() {

        lifecycleScope.launchWhenStarted {
            mainViewModel.savePedidoResult.collect {
                when (it) {
                    is Resource.Success -> {
                        setUpCard(it.data!!, binding.btnEnviarPedido.isVisible)
                    }
                    else -> {
                    }
                }
            }
        }

    }

    private fun observeProducts() {

        lifecycleScope.launchWhenStarted {
            productsViewModel.allProductsResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarPedidoDetail.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarPedidoDetail.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBarPedidoDetail.isVisible = false
                        val products =
                            Gson().fromJson(
                                navArgs.pedido.json_pedido.replace("\\", "").replace("\"", ""),
                                Array<JsonPedido>::class.java
                            ).toList()

                        rcViewAdapter.setItems(it.data?.filter {
                            products.firstOrNull { jsonPedido -> jsonPedido.id_producto == it.id } != null
                        }?.map { pp ->
                            pp.quantity = products.first { f -> f.id_producto == pp.id }.cantidad
                            pp
                        }?.toList() ?: listOf())
                    }
                }
            }
        }

    }

    private fun setUpRcView() {
        val pedido = navArgs.pedido
        rcViewAdapter = PedidoDetailAdapter(if (pedido.isLocal) pedido.products else listOf()) {
            val action =
                PedidoDetailFragmentDirections.actionPedidoDetailFragmentToProductDetailFragment(
                    it,
                    it.nombre,
                    pedido.isLocal
                )
            findNavController().navigate(action)
        }
        binding.rcViewPedidosDetail.apply {
            adapter = rcViewAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    fun sendPedido() {
        mainViewModel.enviarPedido(navArgs.pedido)

        lifecycleScope.launchWhenStarted {
            mainViewModel.enviarPedidoResult.collect {
                when (it) {
                    is Resource.Error -> with(binding) {
                        progressBarPedidoDetail.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarPedidoDetail.isVisible = true
                    }
                    is Resource.Success -> {
                        showDialog(it.data!!)
                    }
                    else -> {
                    }
                }
            }
        }

    }

    private fun showDialog(data: ApiResponse) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("¡Pedido enviado!")
            setMessage(data.mensaje)

            setPositiveButton("Genial!") { d, _ ->
                d.dismiss()
                findNavController().popBackStack()
            }

            show()
        }
    }

}