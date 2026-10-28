package com.ifsul.jet.appjetcomposemvvmcomjava

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Meal
import com.ifsul.jet.appjetcomposemvvmcomjava.utils.MealUiState
import com.ifsul.jet.appjetcomposemvvmcomjava.viewmodels.MealViewModel

@Composable
fun MealScreen(
    viewModel: MealViewModel = viewModel()
) {
    // 1. Observe the UI state from the Java ViewModel
    val uiState by viewModel.uiState.observeAsState(initial = MealUiState.Loading())

    // 2. Local state for the search bar text
    var searchQuery by remember { mutableStateOf("") }

    // Allows us to hide the keyboard when the user presses 'Search'
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        // --- Search Bar Header ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Buscar refeições...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.buscarRefeicoes(searchQuery)
                    focusManager.clearFocus() // Hide keyboard
                }
            ),
            trailingIcon = {
                Button(
                    onClick = {
                        viewModel.buscarRefeicoes(searchQuery)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Go")
                }
            }
        )

        // --- Content Area ---
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is MealUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is MealUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Note: state.meals() because MealUiState.Success is a Java Record
                        items(state.meals()) { meal ->
                            MealCard(meal = meal)
                        }
                    }
                }
                is MealUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Note: state.message() because MealUiState.Error is a Java Record
                        Text(
                            text = state.message(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(onClick = { viewModel.buscarRefeicoes(searchQuery) }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun MealCard(meal: Meal) {
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
            // Meal Thumbnail
            AsyncImage(
                model = meal.thumb(),
                contentDescription = meal.name(),
                modifier = Modifier.size(100.dp)
            )

            // Meal Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = meal.name(),
                    style = MaterialTheme.typography.titleMedium
                )

                // Show Category and Area (e.g., "Vegetarian • Italian")
                Text(
                    text = "${meal.category()} • ${meal.area()}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = meal.instructions() ?: "No instructions available.",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3 // Prevents the card from becoming massive
                )
            }
        }
    }
}