package com.ekachandra.themeal.repository

import android.content.Context
import com.ekachandra.themeal.R
import com.ekachandra.themeal.data.source.remote.network.ApiService
import com.ekachandra.themeal.data.source.remote.response.DetailMealResponse
import com.ekachandra.themeal.data.source.remote.response.FilterByCategoryResponse
import com.ekachandra.themeal.data.source.remote.response.ListAllMealCategoriesResponse
import com.ekachandra.themeal.util.AppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class MealRepositoryImpl(
    private val apiService: ApiService,
    private val context: Context,
) : MealRepository {

    override suspend fun getAllMealCategories(): Flow<AppResult<ListAllMealCategoriesResponse>> {
        return flow {
            try {
                emit(AppResult.Loading)
                val response = apiService.getAllMealCategories()
                val dataArray = response.categories

                if (dataArray.isNullOrEmpty()) {
                    emit(AppResult.Empty)
                } else {
                    emit(AppResult.Success(response))
                }
            } catch (e: HttpException) {
                emit(AppResult.Error(context.getString(R.string.something_went_wrong)))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMealByCategory(category: String): Flow<AppResult<FilterByCategoryResponse>> {
        return flow {
            try {
                emit(AppResult.Loading)
                val response = apiService.getMealByCategory(category)
                val dataArray = response.meals
                if (dataArray.isNullOrEmpty()) {
                    emit(AppResult.Empty)
                } else {
                    emit(AppResult.Success(response))
                }
            } catch (e: HttpException) {
                emit(AppResult.Error(context.getString(R.string.something_went_wrong)))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailMealById(idMeal: String): Flow<AppResult<DetailMealResponse>> {
        return flow {
            try {
                emit(AppResult.Loading)
                val response = apiService.getDetailMealById(idMeal)
                val dataArray = response.meals
                if (dataArray.isNullOrEmpty()) {
                    emit(AppResult.Empty)
                } else {
                    emit(AppResult.Success(response))
                }
            } catch (e: HttpException) {
                emit(AppResult.Error(context.getString(R.string.something_went_wrong)))
            }
        }.flowOn(Dispatchers.IO)
    }
}