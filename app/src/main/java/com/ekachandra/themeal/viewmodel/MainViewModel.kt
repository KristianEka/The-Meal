package com.ekachandra.themeal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekachandra.themeal.data.source.remote.response.DetailMealResponse
import com.ekachandra.themeal.data.source.remote.response.FilterByCategoryResponse
import com.ekachandra.themeal.data.source.remote.response.ListAllMealCategoriesResponse
import com.ekachandra.themeal.repository.MealRepository
import com.ekachandra.themeal.util.AppResult
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MealRepository,
) : ViewModel() {

    private val _allMealCategories = MutableLiveData<AppResult<ListAllMealCategoriesResponse>>()
    val allMealCategories get() = _allMealCategories

    fun getAllMealCategories() {
        viewModelScope.launch {
            repository.getAllMealCategories().collect {
                _allMealCategories.postValue(it)
            }
        }
    }

    private val _mealByCategory = MutableLiveData<AppResult<FilterByCategoryResponse>>()
    val mealByCategory get() = _mealByCategory

    fun getMealByCategory(category: String) {
        viewModelScope.launch {
            repository.getMealByCategory(category).collect {
                _mealByCategory.postValue(it)
            }
        }
    }

    private val _detailMeal = MutableLiveData<AppResult<DetailMealResponse>>()
    val detailMeal get() = _detailMeal

    fun getDetailMealById(idMeal: String) {
        viewModelScope.launch {
            repository.getDetailMealById(idMeal).collect {
                _detailMeal.postValue(it)
            }
        }
    }
}