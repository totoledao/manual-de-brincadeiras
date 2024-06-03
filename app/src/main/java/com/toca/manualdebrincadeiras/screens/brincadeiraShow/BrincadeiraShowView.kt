package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BrincadeiraShowView(
    state: Int? = 0,
    onEvent: Nothing? = null,
    navController: NavHostController
) {
    Scaffold { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Go back",
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
            }
            item { Text(text = state.toString()) }
        }
    }
}