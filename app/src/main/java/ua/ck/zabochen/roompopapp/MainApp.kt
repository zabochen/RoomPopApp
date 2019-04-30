package ua.ck.zabochen.roompopapp

import android.app.Application
import com.facebook.stetho.Stetho

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setStetho()
    }

    private fun setStetho() {
        Stetho.initializeWithDefaults(this)
    }
}