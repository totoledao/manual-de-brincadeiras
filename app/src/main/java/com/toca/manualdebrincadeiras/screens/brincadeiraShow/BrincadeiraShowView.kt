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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.toca.manualdebrincadeiras.screens.brincadeiraShow.components.BottomModal
import com.toca.manualdebrincadeiras.screens.brincadeiraShow.components.WebViewContent

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
                .padding(16.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp, 0.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Go back",
                        modifier = Modifier.clickable { navController.navigateUp() }
                    )

                    FavoriteIcon(
                        isFavorite = state.brincadeira?.brincadeira?.favorito == 1,
                        modifier = Modifier.clickable {
                            onEvent(
                                BrincadeiraShowEvent.UpdateFavorite(
                                    state.brincadeira?.brincadeira?.id!!,
                                    if (state.brincadeira.brincadeira.favorito == 1) 0 else 1
                                )
                            )
                        }
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                ) {
                    Text(
                        text = state.brincadeira?.brincadeira?.nome ?: "",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(text = "ID: ${state.brincadeira?.brincadeira?.id.toString()}")
                    Text(text = "Idade recomendada: ${state.brincadeira?.brincadeira?.idade_min.toString()} a ${state.brincadeira?.brincadeira?.idade_max.toString()} anos")
                    state.brincadeira?.brincadeira?.integrantes?.let {
                        Text(text = "Integrantes: ")
                        Text(text = it)
                    }
                    state.brincadeira?.tipos?.let {
                        Text(text = "Tipos:")
                        it.forEach { tipo ->
                            Text(text = tipo.nome)
                        }
                    }
                    state.brincadeira?.temas?.let {
                        Text(text = "Temas:")
                        it.forEach { tema ->
                            Text(text = tema.nome)
                        }
                    }
                    Text(text = "Descrição:")
                    WebViewContent(state.brincadeira?.brincadeira?.descricao ?: "") {
                        onEvent(BrincadeiraShowEvent.ShowDefinicao(it))
                        onEvent(BrincadeiraShowEvent.ShowHideBottomModal(true))
                    }
                    BottomModal(state.showBottomModal, state.definicao) {
                        onEvent(BrincadeiraShowEvent.ShowHideBottomModal(false))
                    }
                }
            }

        }
    }
}

@Composable
fun FavoriteIcon(isFavorite: Boolean, modifier: Modifier) {
    Icon(
        modifier = modifier,
        imageVector = if (isFavorite) {
            Icons.Filled.Favorite
        } else {
            Icons.Filled.FavoriteBorder
        },
        contentDescription = "Favorite",
    )
}