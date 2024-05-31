package com.toca.manualdebrincadeiras

data class BrincadeiraListState(
    val brincadeiras: List<String> = emptyList(),
    val nome: String = "",
    val sortType: SortType = SortType.ID
)
