package com.example.restaurantws.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantws.core.Resource
import com.example.restaurantws.databinding.FragmentProductsBinding
import com.example.restaurantws.ui.products.adapter.ProductsAdapter
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val categoriesViewModel: ProductsViewModel by viewModel()

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
        categoriesViewModel.loadProducts(args.category.id)
    }

    private fun setUpHeader() = with(binding.productsHeader) {
        titleHeader.text = args.category.nombre
        headerDesc.text = args.category.descripcion
    }

    private fun setUpRcView() {
        rcViewAdapter = ProductsAdapter(mutableListOf())
        binding.rcViewProducts.apply {
            adapter = rcViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeCategories() {
        lifecycleScope.launchWhenStarted {
            categoriesViewModel.signUpResult.collect {
                when (it) {
                    is Resource.Empty -> {
                    }
                    is Resource.Error -> with(binding) {
                        progressBarProduct.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarProduct.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBarProduct.isVisible = false
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