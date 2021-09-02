package com.example.restaurantws.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantws.core.Resource
import com.example.restaurantws.data.main.models.products.Product
import com.example.restaurantws.databinding.FragmentProductsBinding
import com.example.restaurantws.ui.products.adapter.ProductsAdapter
import com.example.restaurantws.ui.products.adapter.ProductsAdapterCallback
import com.example.restaurantws.ui.splash.MainViewModel
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val productsViewModel: ProductsViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var rcViewAdapter: ProductsAdapter

    private val args by navArgs<ProductsFragmentArgs>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRcView()
        observeCategories()
        setUpHeader()
        productsViewModel.loadProducts(args.category.id)
    }

    private fun setUpHeader() = with(binding.productsHeader) {
        titleHeader.text = args.category.nombre
        headerDesc.text = args.category.descripcion
    }

    private fun setUpRcView() {
        rcViewAdapter = ProductsAdapter(mutableListOf(), getAdapterCallback())
        binding.rcViewProducts.apply {
            adapter = rcViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getAdapterCallback(): ProductsAdapterCallback {
        return object : ProductsAdapterCallback {
            override fun onAddItemToCart(product: Product, position: Int) {
                product.quantity++
                mainViewModel.savePedidoWithProduct(product)
                observeProduct(product, position)

            }

            override fun onRemoveItemFromCart(product: Product, position: Int) {
                if (product.quantity == 0) return

                product.quantity--
                mainViewModel.savePedidoWithProduct(product)
                observeProduct(product, position)
            }

            override fun onClickItem(product: Product) {
                val action =
                    ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(
                        product,
                        product.nombre,
                        true
                    )
                findNavController().navigate(action)
            }

            private fun observeProduct(item: Product, position: Int) {
                lifecycleScope.launchWhenStarted {
                    mainViewModel.savePedidoResult.collect {
                        when (it) {
                            is Resource.Error -> toast(it.error?.message)
                            is Resource.Success -> {
                                rcViewAdapter.updateItem(item, position)
                            }
                            else -> {
                            }
                        }
                    }
                }

            }
        }
    }

    private fun observeCategories() {
        lifecycleScope.launchWhenStarted {
            productsViewModel.productsResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarProduct.isVisible = false
                        cardProducts.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarProduct.isVisible = true
                        cardProducts.isVisible = false
                    }
                    is Resource.Success -> {
                        binding.progressBarProduct.isVisible = false
                        binding.cardProducts.isVisible = true
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