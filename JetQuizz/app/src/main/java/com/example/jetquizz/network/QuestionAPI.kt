package com.example.jetquizz.network

import com.example.jetquizz.model.Question
import retrofit2.http.GET

interface QuestionAPI {
    @GET("itmmckernan/triviaJSON/master/world.json")
    suspend fun getAllQuestion(): Question
}