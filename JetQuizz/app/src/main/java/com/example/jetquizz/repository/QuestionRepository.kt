package com.example.jetquizz.repository

import com.example.jetquizz.data.DataOrException
import com.example.jetquizz.model.QuestionItem
import com.example.jetquizz.network.QuestionAPI
import javax.inject.Inject
import kotlin.Exception

class QuestionRepository @Inject constructor(private val api: QuestionAPI) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestion() : DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestion()
            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (exception: Exception) {
            dataOrException.e = exception
        }
        return dataOrException
    }
}