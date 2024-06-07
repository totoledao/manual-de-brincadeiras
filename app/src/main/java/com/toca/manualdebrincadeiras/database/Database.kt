package com.toca.manualdebrincadeiras.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Brincadeira::class, Tipo::class, BrincadeiraTipo::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val brincadeiraDao: BrincadeiraDao
    abstract val tipoDao: TipoDao
}