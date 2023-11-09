package uz.gita.todoapp_mehriddin.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.todoapp_mehriddin.data.source.room.MyDatabase
import uz.gita.todoapp_mehriddin.data.source.room.dao.CategoryDao
import uz.gita.todoapp_mehriddin.data.source.room.dao.TaskDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context):MyDatabase =
    Room.databaseBuilder(context,MyDatabase::class.java,"taskDatas.db").allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideDao(db:MyDatabase):TaskDao = db.getTaskDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db:MyDatabase):CategoryDao = db.getCategoryDao()
}