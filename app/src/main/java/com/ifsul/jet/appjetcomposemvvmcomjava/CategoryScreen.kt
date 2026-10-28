package com.ifsul.jet.appjetcomposemvvmcomjava

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Category
import com.ifsul.jet.appjetcomposemvvmcomjava.utils.CategoryUiState
import com.ifsul.jet.appjetcomposemvvmcomjava.viewmodels.CategoryViewModelJava

@Composable
fun CategoryScreen(
    // Injects your Java ViewModel
    viewModel: CategoryViewModelJava = viewModel()
) {
    // Observes the LiveData. We provide Loading() as the initial default state.
    val uiState by viewModel.uiState.observeAsState(initial = CategoryUiState.Loading())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is CategoryUiState.Loading -> {
                CircularProgressIndicator()
            }
            is CategoryUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Note the parentheses on state.categories() because it's a Java Record
                    items(state.categories()) { category ->
                        CategoryCard(category = category)
                    }
                }
            }
            is CategoryUiState.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Note the parentheses on state.message()
                    Text(
                        text = state.message(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(onClick = { viewModel.fetchCategories() }) {
                        Text("Retry")
                    }
                }
            }
            else -> {} // Kotlin sometimes requires an else branch for Java sealed interfaces
        }
    }
}

@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Note: Since Category is also a Java Record, use parentheses for its fields too!
            AsyncImage(
                model = category.strThumb(),
                contentDescription = category.str(),
                modifier = Modifier.size(80.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = category.str(),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = category.strDescription(),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
            }
        }
    }
}