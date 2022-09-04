package com.pleiades.pleione.musinsamultitab.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UrlString::class], version = 1)
abstract class UrlStringDatabase :RoomDatabase(){
    abstract fun urlStringDao() : UrlStringDao
}