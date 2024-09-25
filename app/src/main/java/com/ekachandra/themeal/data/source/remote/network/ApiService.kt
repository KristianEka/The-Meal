package com.ekachandra.themeal.data.source.remote.network

import com.ekachandra.themeal.data.source.remote.response.DetailMealResponse
import com.ekachandra.themeal.data.source.remote.response.FilterByCategoryResponse
import com.ekachandra.themeal.data.source.remote.response.ListAllMealCategoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getAllMealCategories(
    ): ListAllMealCategoriesResponse

    @GET("filter.php")
    suspend fun getMealByCategory(
        @Query("c") category: String
    ): FilterByCategoryResponse

    @GET("lookup.php")
    suspend fun getDetailMealById(
        @Query("i") idMeal: String
    ): DetailMealResponse
}