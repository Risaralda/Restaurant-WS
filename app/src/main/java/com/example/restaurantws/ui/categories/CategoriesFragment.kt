package com.example.restaurantws.ui.categories

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
import com.example.restaurantws.databinding.FragmentCategoriesBinding
import com.example.restaurantws.utils.toast
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val categoriesViewModel: CategoriesViewModel by viewModel()

    private lateinit var rcViewAdapter: CategoriesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRcView()
        observeCategories()
        categoriesViewModel.loadCategories()

    }

    private fun setUpRcView() {
        rcViewAdapter = CategoriesAdapter(listOf()) {
            val action =
                CategoriesFragmentDirections.actionFirstFragmentToProductsFragment(it, it.nombre)
            findNavController().navigate(action)
        }

        binding.rcViewCategories.apply {
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
                        progressBarCategory.isVisible = false
                        categoriesCard.isVisible = false
                        toast(it.error?.message)
                    }
                    is Resource.Loading -> with(binding) {
                        progressBarCategory.isVisible = true
                        categoriesCard.isVisible = false
                    }
                    is Resource.Success -> {
                        binding.progressBarCategory.isVisible = false
                        binding.categoriesCard.isVisible = true
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