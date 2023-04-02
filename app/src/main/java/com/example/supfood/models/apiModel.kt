package com.example.supfood

import androidx.room.Entity

@Entity
data class Api (
        val count: Int,
        val next: String,
        val previous: String,
        val results: List<Results>
        )

@Entity
data class Results(
    val pk: Int,
    val title: String,
    val publisher: String,
    val featured_image: String,
    val rating: Int,
    val source_url: String,
    val description: String,
    val cooking_instructions: String,
    val ingredients: List<String>,
    val date_added: String,
    val date_updated: String,
    val long_date_added: String,
    val long_date_updated: String
)

@Entity
data class ApiDetail(
    val pk: Int,
    val title: String,
    val publisher: String,
    val featured_image: String,
    val rating: Int,
    val source_url: String,
    val description: String,
    val cooking_instructions: String,
    val ingredients: List<String>,
    val date_added: String,
    val date_updated: String,
    val long_date_added: Int,
    val long_date_updated: Int
)