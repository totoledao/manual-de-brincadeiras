package com.toca.manualdebrincadeiras.screens.brincadeiraList

sealed interface BrincadeiraListEvent {
    data class OnNameChange(val name: String) : BrincadeiraListEvent
    data class OnMinAgeChange(val minAge: Int) : BrincadeiraListEvent
    data class OnMaxAgeChange(val maxAge: Int) : BrincadeiraListEvent
    data class OnIsFavoriteChange(val isFavorite: Int?) : BrincadeiraListEvent
    data class OnTypeIdsChange(val typeIds: Int) : BrincadeiraListEvent
}