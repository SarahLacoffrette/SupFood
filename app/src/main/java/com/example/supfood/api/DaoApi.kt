package com.example.supfood.api

import androidx.room.Dao
import androidx.room.Query
import com.example.supfood.Api
import com.example.supfood.ApiDetail
import com.example.supfood.Results

@Dao
interface DaoApi {

    @Query("SELECT * FROM Api")
    fun getAll(): Api

    @Query("SELECT * FROM Results WHERE title = :title")
    fun getElements(title: String): Results

    @Query("SELECT * FROM ApiDetail WHERE pk = :id")
    fun getDetail(id: String): ApiDetail
}