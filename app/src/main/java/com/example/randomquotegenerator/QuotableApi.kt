package com.example.randomquotegenerator

import retrofit2.http.GET
import retrofit2.http.Query

interface QuotableApi {

        @GET("quotes/random")
        suspend fun getRandomQuotes(
            @Query("limit") limit: Int = 1,
            @Query("maxLength") maxLength: Int? = null,
            @Query("minLength") minLength: Int? = null,
            @Query("tags") tags: String? = null,
            @Query("author") author: String? = null
        ): List<Quote>
    }

