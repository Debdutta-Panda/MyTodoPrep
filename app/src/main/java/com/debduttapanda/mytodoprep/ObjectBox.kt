package com.debduttapanda.mytodoprep

import android.content.Context
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
                .androidContext(context.applicationContext)
                .build()
        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(store).start(context)
        }
    }
}