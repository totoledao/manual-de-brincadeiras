package com.toca.manualdebrincadeiras.screens.brincadeiraList

import com.toca.manualdebrincadeiras.database.BrincadeiraIndex

data class BrincadeiraListState(
    val brincadeiras: List<BrincadeiraIndex> = emptyList(),
    val nome: String = "",
    val sortType: SortType = SortType.ID
)
