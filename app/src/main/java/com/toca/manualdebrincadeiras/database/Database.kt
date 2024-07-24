package com.toca.manualdebrincadeiras.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Brincadeira::class, Tipo::class, Tema::class, BrincadeiraTipo::class, BrincadeiraTema::class, Definicao::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract val brincadeiraDao: BrincadeiraDao
    abstract val tipoDao: TipoDao
    abstract val temaDao: TemaDao
    abstract val glossarioDao: GlossarioDao
}