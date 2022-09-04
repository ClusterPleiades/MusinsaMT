package com.pleiades.pleione.musinsamultitab

class Config {
    companion object{
        // room
        const val DATABASE_NAME = "url_db"
        const val TABLE_NAME = "url_table"

        // prefs
        const val PREFS = "prefs"
        const val KEY_CURRENT_TAB_URL_STRING = "current_tab_url_string"
        const val KEY_CURRENT_TAB_TIME = "current_tab_time"
        const val DEFAULT_URL = "https://m.store.musinsa.com/"

        // fragment
        const val KEY_STACK = "stack"
    }
}