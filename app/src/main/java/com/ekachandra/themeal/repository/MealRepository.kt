package com.ekachandra.themeal.repository

import com.ekachandra.themeal.data.source.remote.response.DetailMealResponse
import com.ekachandra.themeal.data.source.remote.response.FilterByCategoryResponse
import com.ekachandra.themeal.data.source.remote.response.ListAllMealCategoriesResponse
import com.ekachandra.themeal.util.AppResult
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    suspend fun getAllMealCategories(): Flow<AppResult<ListAllMealCategoriesResponse>>

    suspend fun getMealByCategory(category: String): Flow<AppResult<FilterByCategoryResponse>>

    suspend fun getDetailMealById(idMeal: String): Flow<AppResult<DetailMealResponse>>
}