package ua.ck.zabochen.roompopapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import ua.ck.zabochen.roompopapp.R
import ua.ck.zabochen.roompopapp.database.UserDatabase
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pre populate database maybe, if we create query to database -> "getUser()" for example.
        // Only in this situation will work method "onCreate" in Callback in DatabaseBuilder.
        prepopulateDatabaseVersion2()
        setUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.job.cancel()
    }

    private fun prepopulateDatabaseVersion1() = launch {
        withContext(Dispatchers.IO) {
            UserDatabase.getInstance(applicationContext).userDao().getUser()
        }
    }

    private fun prepopulateDatabaseVersion2() = launch {
        withContext(Dispatchers.IO) {
            UserDatabase(applicationContext).userDao().getUser()
        }
    }

    private fun setUI() {
        // Layout
        setContentView(R.layout.activity_main)
    }
}
