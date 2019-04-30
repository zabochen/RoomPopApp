package ua.ck.zabochen.roompopapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String
)