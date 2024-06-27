package com.toca.manualdebrincadeiras.screens.brincadeiraShow

sealed interface BrincadeiraShowEvent {
    data class ShowBrincadeira(val id: Int) : BrincadeiraShowEvent
    data class UpdateFavorite(val id: Int, val fav: Int) : BrincadeiraShowEvent
    data class ShowDefinicao(val id: Int) : BrincadeiraShowEvent
    data class ShowHideBottomModal(val show: Boolean = false) : BrincadeiraShowEvent
}