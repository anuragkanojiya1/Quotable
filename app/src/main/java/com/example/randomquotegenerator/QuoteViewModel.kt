package com.example.randomquotegenerator

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuoteViewModel : ViewModel() {
    private val _quotes = MutableLiveData<List<Quote>>()
    val quotes: LiveData<List<Quote>> get() = _quotes

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchRandomQuotes(limit: Int = 1) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomQuotes(limit)
                _quotes.postValue(response)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _errorMessage.postValue("Error fetching quotes")
            }
        }
    }
}

