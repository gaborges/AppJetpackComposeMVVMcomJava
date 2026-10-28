package com.ifsul.jet.appjetcomposemvvmcomjava.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Category
import com.ifsul.jet.appjetcomposemvvmcomjava.utils.CategoryUiStateKotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModelKotlin : ViewModel() {

    // 1. Hold a single state flow for the entire UI state
    private val _uiState = MutableStateFlow<CategoryUiStateKotlin>(CategoryUiStateKotlin.Loading)
    val uiState: StateFlow<CategoryUiStateKotlin> = _uiState.asStateFlow()

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        viewModelScope.launch {
            // Set state to Loading immediately
            _uiState.value = CategoryUiStateKotlin.Loading

            try {
                // TODO: Replace this simulated delay and mock list with your actual Retrofit API call, e.g.:
                // val response = api.getCategories()
                // _uiState.value = CategoryUiState.Success(response.categories)

                delay(1500) // Simulating network lag

                // Mock success result
                val mockList = listOf(
                    Category("1", "Beef", "https://www.themealdb.com/images/category/beef.png", "Beef is the culinary name for meat from cattle."),
                    Category("2", "Chicken", "https://www.themealdb.com/images/category/chicken.png", "Chicken is a type of domesticated fowl."),
                    Category("3", "Dessert", "https://www.themealdb.com/images/category/dessert.png", "Dessert is a course that concludes a meal.")
                )
                _uiState.value = CategoryUiStateKotlin.Success(mockList)

            } catch (e: Exception) {
                // Capture any network or parsing errors
                _uiState.value = CategoryUiStateKotlin.Error(e.localizedMessage ?: "Failed to load categories")
            }
        }
    }
}