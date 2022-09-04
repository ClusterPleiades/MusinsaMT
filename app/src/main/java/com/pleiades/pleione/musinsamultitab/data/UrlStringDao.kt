package com.pleiades.pleione.musinsamultitab.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UrlStringDao {
    @Query("SELECT * FROM urlString") // ORDER BY time ASC
    fun getUrlStrings() : Flow<List<UrlString>>

    @Insert
    fun insertNote(urlString : UrlString)

    @Update
    fun updateNote(urlString : UrlString)

    @Delete
    fun deleteNote(urlString: UrlString)
}