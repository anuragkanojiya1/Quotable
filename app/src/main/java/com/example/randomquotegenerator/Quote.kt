package com.example.randomquotegenerator

data class Quote(
    val _id: String,
    val content: String,
    val author: String,
    val authorSlug: String,
    val length: Int,
    val tags: List<String>
)
