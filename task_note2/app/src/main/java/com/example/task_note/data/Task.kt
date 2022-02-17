package com.example.task_note.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID
//tao database voi Room voi id la primaryKey
@Entity(tableName = "taskTable")
@Parcelize
data class Task(
    var title: String,
    val description:String,
    @PrimaryKey val id:String = UUID.randomUUID().toString()
): Parcelable {
    fun isContentEqual(other: Task): Boolean = title == other.title && description == other.description
}