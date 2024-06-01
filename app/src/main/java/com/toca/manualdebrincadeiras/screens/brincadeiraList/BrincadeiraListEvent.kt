package com.toca.manualdebrincadeiras.screens.brincadeiraList

sealed interface BrincadeiraListEvent {
    data class GetBrincadeiras(val sortType: SortType) : BrincadeiraListEvent
    data class SortBrincadeiras(val sortType: SortType) : BrincadeiraListEvent
    data class SetNome(val nome: String) : BrincadeiraListEvent
}