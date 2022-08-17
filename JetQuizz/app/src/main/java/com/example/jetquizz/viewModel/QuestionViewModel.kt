package com.example.jetquizz.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetquizz.data.DataOrException
import com.example.jetquizz.model.QuestionItem
import com.example.jetquizz.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) : ViewModel() {
    private val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>>
        = mutableStateOf(DataOrException(null, true, Exception("")))
    val questions = data?.value?.data
    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestion()
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }

    private fun getTotalQuestionsCount(): Int {
        return data.value.data?.toMutableList()?.size!!
    }
}