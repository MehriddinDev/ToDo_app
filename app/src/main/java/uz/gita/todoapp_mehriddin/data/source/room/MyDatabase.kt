package uz.gita.todoapp_mehriddin.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.todoapp_mehriddin.data.source.room.dao.CategoryDao
import uz.gita.todoapp_mehriddin.data.source.room.dao.TaskDao
import uz.gita.todoapp_mehriddin.data.source.room.entity.CategoryEntity
import uz.gita.todoapp_mehriddin.data.source.room.entity.TaskEntity

@Database(entities = [TaskEntity::class, CategoryEntity::class], version = 1, exportSchema = false)
abstract class MyDatabase :RoomDatabase(){
    abstract fun getTaskDao():TaskDao
    abstract fun getCategoryDao():CategoryDao
}