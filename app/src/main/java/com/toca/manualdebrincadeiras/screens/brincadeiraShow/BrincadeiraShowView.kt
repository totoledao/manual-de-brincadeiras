package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BrincadeiraShowView(
    state: BrincadeiraShowState,
    onEvent: (BrincadeiraShowEvent) -> Unit,
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
                Button(
                    onClick = { onEvent(BrincadeiraShowEvent.ShowBrincadeira(state.id)) }

                ) {
                    Text(text = "Show id ${state.id}")
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Go back",
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )

                    FavoriteIcon(isFavorite = state.brincadeira?.favorito == 1)
                }
            }
            item {
                Column() {
                    Text(text = state.brincadeira?.id.toString() ?: "")
                    Text(text = state.brincadeira?.nome ?: "")
                    Text(text = state.brincadeira?.idade_min.toString() ?: "")
                    Text(text = state.brincadeira?.idade_max.toString() ?: "")
                    Text(text = state.brincadeira?.descricao ?: "")
                }
            }

        }
    }
}

@Composable
fun FavoriteIcon(isFavorite: Boolean) {
    Icon(
        imageVector = if (isFavorite) {
            Icons.Filled.Favorite
        } else {
            Icons.Filled.FavoriteBorder
        },
        contentDescription = "Favorite",
    )
}