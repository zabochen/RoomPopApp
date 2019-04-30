package ua.ck.zabochen.roompopapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG: String = "UserDatabase"

@Database(
    version = 1,
    entities = [UserEntity::class]
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var userDatabaseInstance: UserDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context): UserDatabase {
            return userDatabaseInstance ?: synchronized<UserDatabase>(lock) {
                return userDatabaseInstance ?: buildDatabase(context).also { userDatabaseInstance = it }
            }
        }

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                if (userDatabaseInstance == null) {
                    userDatabaseInstance = buildDatabase(context)
                }
                return userDatabaseInstance!!
            }
        }

        private fun buildDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.i(TAG, "Room: onCreate()")
                        GlobalScope.launch {
                            withContext(Dispatchers.IO) {
                                prepopulateUserDatabase(getInstance(context))
                            }
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.i(TAG, "Room: onOpen()")
                    }
                })
                .build()
        }

        private fun prepopulateUserDatabase(userDatabase: UserDatabase) {
            userDatabase.userDao()
                .insert(UserEntity(name = "User_01"))
        }
    }

}