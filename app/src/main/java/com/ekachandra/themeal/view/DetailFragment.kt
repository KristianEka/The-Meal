package com.ekachandra.themeal.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ekachandra.themeal.databinding.FragmentDetailBinding
import com.ekachandra.themeal.util.AppResult
import com.ekachandra.themeal.view.recyclerview.IngredientAdapter
import com.ekachandra.themeal.viewmodel.MainViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            showDetail()
        }
    }

    private fun showDetail() {
        val idMeal = args.idMeal
        viewModel.getDetailMealById(idMeal)
        viewModel.detailMeal.observe(viewLifecycleOwner) { result ->
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

                        val data = result.data.meals?.first()

                        if (data != null) {
                            binding.apply {
                                appbar.tvTitle.text = data.strMeal

                                Glide.with(requireContext())
                                    .load(data.strMealThumb)
                                    .into(ivThumb)

                                tvCategory.text = data.strCategory
                                tvArea.text = data.strArea
                                tvInstructions.text = data.strInstructions

                                val ingredients = listOf(
                                    "${data.strMeasure1} ${data.strIngredient1}",
                                    "${data.strMeasure2} ${data.strIngredient2}",
                                    "${data.strMeasure3} ${data.strIngredient3}",
                                    "${data.strMeasure4} ${data.strIngredient4}",
                                    "${data.strMeasure5} ${data.strIngredient5}",
                                    "${data.strMeasure6} ${data.strIngredient6}",
                                    "${data.strMeasure7} ${data.strIngredient7}",
                                    "${data.strMeasure8} ${data.strIngredient8}",
                                    "${data.strMeasure9} ${data.strIngredient9}",
                                    "${data.strMeasure10} ${data.strIngredient10}",
                                    "${data.strMeasure11} ${data.strIngredient11}",
                                    "${data.strMeasure12} ${data.strIngredient12}",
                                    "${data.strMeasure13} ${data.strIngredient13}",
                                    "${data.strMeasure14} ${data.strIngredient14}",
                                    "${data.strMeasure15} ${data.strIngredient15}",
                                    "${data.strMeasure16} ${data.strIngredient16}",
                                    "${data.strMeasure17} ${data.strIngredient17}",
                                    "${data.strMeasure18} ${data.strIngredient18}",
                                    "${data.strMeasure19} ${data.strIngredient19}",
                                    "${data.strMeasure20} ${data.strIngredient20}",
                                ).filter { it.isNotBlank() }

                                setupAdapter(ingredients)
                            }
                        } else {
                            stateEmpty(true)
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

    private fun setupAdapter(items: List<String>) {
        ingredientAdapter = IngredientAdapter(items)
        binding.apply {
            rvIngredients.layoutManager = GridLayoutManager(requireContext(), 2)
            rvIngredients.setHasFixedSize(true)
            rvIngredients.adapter = ingredientAdapter
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