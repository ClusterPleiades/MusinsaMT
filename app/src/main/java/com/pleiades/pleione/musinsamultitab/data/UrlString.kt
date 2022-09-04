package com.pleiades.pleione.musinsamultitab.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UrlString(
    @PrimaryKey val time: Long,
    @ColumnInfo val urlString: String
)