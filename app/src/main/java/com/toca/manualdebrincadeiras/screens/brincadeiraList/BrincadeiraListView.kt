package com.toca.manualdebrincadeiras.screens.brincadeiraList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp))
                        .background(Color.LightGray)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Text(
                        "Busca",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextField(
                        label = { Text("Nome") },
                        value = state.name,
                        onValueChange = {
                            onEvent(BrincadeiraListEvent.OnNameChange(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.White,
                            unfocusedTextColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = state.isFavorite == 1,
                            onCheckedChange = {
                                onEvent(
                                    BrincadeiraListEvent.OnIsFavoriteChange(
                                        if (it) 1 else null
                                    )
                                )
                            }
                        )
                        Text(
                            "Favoritos"
                        )
                    }

                    Column {
                        Text(
                            "Idade mínima: ${state.minAge} anos",
                        )
                        Slider(
                            value = state.minAge.toFloat(),
                            onValueChange = {
                                onEvent(BrincadeiraListEvent.OnMinAgeChange(it.toInt()))
                            },
                            valueRange = 3f..16f,
                            steps = 16,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("3")
                            Text("16+")
                        }
                    }

                    Column {
                        Text(
                            "Idade máxima: ${state.maxAge} anos",
                        )
                        Slider(
                            value = state.maxAge.toFloat(),
                            onValueChange = {
                                onEvent(BrincadeiraListEvent.OnMaxAgeChange(it.toInt()))
                            },
                            valueRange = 3f..16f,
                            steps = 16,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("3")
                            Text("16+")
                        }
                    }

                    state.tipos.let {
                        Text(text = "Tipos:")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
                            it.forEach { tipo ->
                                Button(
                                    onClick = {
                                        onEvent(BrincadeiraListEvent.OnTypeIdsChange(tipo.id))
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = (if (state.typeIds?.contains(tipo.id) == true) Color.Green else Color.White),
                                        contentColor = Color.Black
                                    )
                                ) {
                                    Text(text = tipo.nome)

                                }
                            }
                        }
                    }
                }
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