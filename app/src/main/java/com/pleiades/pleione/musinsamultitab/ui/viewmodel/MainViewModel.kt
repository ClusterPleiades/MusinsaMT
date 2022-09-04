package com.pleiades.pleione.musinsamultitab.ui.viewmodel

import android.app.Application
import android.provider.ContactsContract
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.pleiades.pleione.musinsamultitab.Config.Companion.TABLE_NAME
import com.pleiades.pleione.musinsamultitab.data.UrlString
import com.pleiades.pleione.musinsamultitab.data.UrlStringDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Room.databaseBuilder(getApplication<Application>().applicationContext, UrlStringDatabase::class.java, TABLE_NAME).build()
    private val dao = database.urlStringDao()
    val notes = dao.getUrlStrings()

    @WorkerThread
    fun insert(urlString: UrlString) {
        dao.insertNote(urlString)
    }

    @WorkerThread
    fun update(urlString: UrlString) {
        dao.updateNote(urlString)
    }

    @WorkerThread
    fun delete(urlString: UrlString) {
        dao.deleteNote(urlString)
    }
}