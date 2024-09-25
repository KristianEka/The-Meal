package com.ekachandra.themeal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ekachandra.themeal.databinding.FragmentMealBinding
import com.ekachandra.themeal.util.AppResult
import com.ekachandra.themeal.view.recyclerview.MealAdapter
import com.ekachandra.themeal.viewmodel.MainViewModel

class MealFragment : Fragment() {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!
    private val args: MealFragmentArgs by navArgs()
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var mealAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            binding.appbar.tvTitle.text = args.strCategory
            setupAdapter()
            showMeals()
        }
    }

    private fun setupAdapter() {
        mealAdapter = MealAdapter()
        binding.apply {
            rvMeals.layoutManager = GridLayoutManager(requireContext(), 2)
            rvMeals.setHasFixedSize(true)
            rvMeals.adapter = mealAdapter
        }
    }

    private fun showMeals() {
        val strCategory = args.strCategory
        viewModel.getMealByCategory(strCategory)
        viewModel.mealByCategory.observe(viewLifecycleOwner) { result ->
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
                        if (result.data.meals.isNullOrEmpty()) {
                            stateEmpty(true)
                        } else {
                            mealAdapter.submitList(result.data.meals)
                            mealAdapter.onItemClick = {
                                val action =
                                    MealFragmentDirections.actionFoodFragmentToDetailFragment(
                                        it.idMeal.toString()
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