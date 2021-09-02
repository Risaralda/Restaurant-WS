package com.example.restaurantws.ui.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.restaurantws.R
import com.example.restaurantws.core.Resource
import com.example.restaurantws.databinding.FragmentProductDetailBinding
import com.example.restaurantws.ui.splash.MainViewModel
import com.example.restaurantws.utils.load
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val mainViewModel: MainViewModel by sharedViewModel()

    private val args by navArgs<ProductDetailFragmentArgs>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindData()
    }

    private fun bindData() = with(binding) {
        val product = args.producto
        val isLocal = args.isLocalPedido

        imgRemoveFromCart.apply {
            isVisible = product.quantity > 0 && isLocal
            setOnClickListener { removeItemFromCart() }
        }

        btnAddProduct.apply {
            isVisible = isLocal
            setOnClickListener { addItemToCart() }
        }

        productName.text = product.nombre
        productDesc.text = product.descripcion
        "$ ${product.precio}".also { productPrice.text = it }

        imgProduct.load(product.url_imagen)

        productQuantity.apply {
            isVisible = product.quantity > 0
            text = product.quantity.toString()
        }

        btnGoToCategories.setOnClickListener {
            findNavController().popBackStack(R.id.FirstFragment, false)
        }
    }

    private fun addItemToCart() {
        val product = args.producto
        product.quantity++
        mainViewModel.savePedidoWithProduct(product)
        observeProduct()

    }

    private fun removeItemFromCart() {
        val product = args.producto
        if (product.quantity == 0) return

        product.quantity--
        mainViewModel.savePedidoWithProduct(product)
        observeProduct()
    }

    private fun observeProduct() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.savePedidoResult.collect {
                when (it) {
                    is Resource.Error -> toast(it.error?.message)
                    is Resource.Success -> {
                        bindData()
                    }
                    else -> {
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