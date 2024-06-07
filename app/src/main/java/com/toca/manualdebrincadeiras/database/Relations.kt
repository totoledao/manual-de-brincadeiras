package com.toca.manualdebrincadeiras.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BrincadeiraWithTipo(
    @Embedded val brincadeira: Brincadeira,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = BrincadeiraTipo::class,
            parentColumn = "brincadeira_id",
            entityColumn = "tipo_id"
        )
    )
    val tipos: List<Tipo> = emptyList()
)