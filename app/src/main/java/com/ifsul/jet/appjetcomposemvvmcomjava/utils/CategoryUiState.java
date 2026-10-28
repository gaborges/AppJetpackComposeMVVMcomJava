package com.ifsul.jet.appjetcomposemvvmcomjava.utils;

import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Category;

import java.util.List;

// The "permits" keyword restricts which classes/records can implement this interface
public sealed interface CategoryUiState permits
        CategoryUiState.Loading,
        CategoryUiState.Success,
        CategoryUiState.Error {

    // Records automatically generate the constructor, getters, equals, and hashCode
    record Loading() implements CategoryUiState {}

    record Success(List<Category> categories) implements CategoryUiState {}

    record Error(String message) implements CategoryUiState {}
}
