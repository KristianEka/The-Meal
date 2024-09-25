package com.ekachandra.themeal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ekachandra.themeal.R
import com.ekachandra.themeal.databinding.FragmentCategoryBinding
import com.ekachandra.themeal.util.AppResult
import com.ekachandra.themeal.view.recyclerview.CategoryAdapter
import com.ekachandra.themeal.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModel<MainViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.appbar.tvTitle.text = getString(R.string.categories)
            setupAdapter()
            showCategories()
        }
    }

    private fun setupAdapter() {
        categoryAdapter = CategoryAdapter()
        binding.apply {
            rvCategory.layoutManager = GridLayoutManager(requireContext(), 2)
            rvCategory.setHasFixedSize(true)
            rvCategory.adapter = categoryAdapter
        }
    }

    private fun showCategories() {
        viewModel.getAllMealCategories()
        viewModel.allMealCategories.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is AppResult.Loading -> {
                        stateLoading(true)
                        stateEmpty(false)
                    }

                    is AppResult.Empty -> {
                        stateLoading(false)
                        stateEmpty(true)
                    }

                    is AppResult.Success -> {
                        stateLoading(false)
                        if (result.data.categories.isNullOrEmpty()) {
                            stateEmpty(true)
                        } else {
                            categoryAdapter.submitList(result.data.categories)
                            categoryAdapter.onItemClick = {
                                val action =
                                    CategoryFragmentDirections.actionCategoryFragmentToFoodFragment(
                                        it.strCategory.toString()
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    }

                    is AppResult.Error -> {
                        stateLoading(false)
                        stateEmpty(false)
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun stateLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun stateEmpty(isEmpty: Boolean) {
        binding.apply {
            viewEmpty.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
        }
    }

}