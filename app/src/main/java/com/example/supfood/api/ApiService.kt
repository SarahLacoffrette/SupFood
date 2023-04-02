package com.example.supfood.api

import com.example.supfood.Api
import com.example.supfood.ApiDetail
import com.example.supfood.Results
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
    @GET("api/recipe/search/?page=2&query=beef%20carrot%20potato%20onion")
    suspend fun fetchData(): Api

    @Headers("Authorization: Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
    @GET("api/recipe/search/")
    suspend fun fetchRecipe(@Query("query") query: String): Api

    @Headers("Authorization: Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
    @GET("api/recipe/get/")
    suspend fun fetchDetail(@Query("id") id: String): ApiDetail

}

suspend fun fetchApiData(): Api {
    return RetrofitClient.instance.fetchData()
}

suspend fun fetchRecipeData(query: String): Api {
    return RetrofitClient.instance.fetchRecipe(query)
}

suspend fun fetchDetailData(id: String): ApiDetail {
    return RetrofitClient.instance.fetchDetail(id)
}