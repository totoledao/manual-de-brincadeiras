package com.toca.manualdebrincadeiras.screens.brincadeiraShow

import com.toca.manualdebrincadeiras.database.BrincadeiraWithTipo

data class BrincadeiraShowState(
    val id: Int = 0,
    val brincadeira: BrincadeiraWithTipo? = null,
)
