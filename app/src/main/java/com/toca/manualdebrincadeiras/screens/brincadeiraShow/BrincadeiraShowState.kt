package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import com.toca.manualdebrincadeiras.database.Brincadeira

data class BrincadeiraShowState(
    val id: Int = 0,
    val brincadeira: Brincadeira? = null,
)
