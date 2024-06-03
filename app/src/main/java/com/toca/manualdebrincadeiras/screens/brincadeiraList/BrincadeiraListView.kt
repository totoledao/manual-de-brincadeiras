package com.toca.manualdebrincadeiras.screens.brincadeiraList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.toca.manualdebrincadeiras.Routes

@Composable
fun BrincadeiraListView(
    state: BrincadeiraListState,
    onEvent: (BrincadeiraListEvent) -> Unit,
    navController: NavHostController
) {
    Scaffold { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SortType.entries.forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(
                                        BrincadeiraListEvent.SortBrincadeiras(
                                            sortType
                                        )
                                    )
                                }
                                .padding(0.dp, 0.dp, 16.dp, 0.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(
                                        BrincadeiraListEvent.SortBrincadeiras(
                                            sortType
                                        )
                                    )
                                })
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            item {
                TextField(
                    label = { Text("Nome") },
                    value = state.nome,
                    onValueChange = {
                        onEvent(BrincadeiraListEvent.SetNome(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search for games",
                        )
                    },
                )
            }
            items(state.brincadeiras) { brincadeira ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp)
                        .clickable { navController.navigate(Routes.Show.name + "/" + brincadeira.id) },
                ) {
                    Text(text = brincadeira.nome, fontSize = 20.sp)
                }
            }
        }
    }
}