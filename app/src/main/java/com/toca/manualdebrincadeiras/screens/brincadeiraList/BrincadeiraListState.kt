package com.toca.manualdebrincadeiras.screens.brincadeiraList

import com.toca.manualdebrincadeiras.database.BrincadeiraIndex
import com.toca.manualdebrincadeiras.database.Tema
import com.toca.manualdebrincadeiras.database.Tipo

data class BrincadeiraListState(
    val brincadeiras: List<BrincadeiraIndex> = emptyList(),
    val tipos: List<Tipo> = emptyList(),
    val temas: List<Tema> = emptyList(),
    val name: String = "",
    val minAge: Int = 3,
    val maxAge: Int = 16,
    val isFavorite: Int? = null,
    val typeIds: List<Int> = emptyList(),
    val themeIds: List<Int> = emptyList()
)
