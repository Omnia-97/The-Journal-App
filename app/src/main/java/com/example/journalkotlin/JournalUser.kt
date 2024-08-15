package com.example.journalkotlin

import android.app.Application

class JournalUser : Application() {
    val username: String? = null
    val userId: String? = null

    companion object {
        var instance: JournalUser? = null
            get() {
                if (field == null) {
                    field = JournalUser()
                }
                return field
            }
            private set
    }
}
