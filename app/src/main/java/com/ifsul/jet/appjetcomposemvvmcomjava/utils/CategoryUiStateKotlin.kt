package com.ifsul.jet.appjetcomposemvvmcomjava.utils

import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Category

sealed interface CategoryUiStateKotlin {
    object Loading : CategoryUiStateKotlin
    data class Success(val categories: List<Category>) : CategoryUiStateKotlin
    data class Error(val message: String) : CategoryUiStateKotlin
}