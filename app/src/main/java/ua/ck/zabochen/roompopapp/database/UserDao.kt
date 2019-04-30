package ua.ck.zabochen.roompopapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insert(userEntity: UserEntity)

    @Query(value = "SELECT * FROM user")
    fun getUser(): List<UserEntity>
}