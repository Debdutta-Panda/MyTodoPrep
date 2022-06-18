package com.debduttapanda.mytodoprep

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}