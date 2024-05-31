package com.toca.manualdebrincadeiras

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Brincadeira::class],
    version = 1
)
abstract class BrincadeiraDatabase : RoomDatabase() {
    abstract val dao: BrincadeiraDao
}