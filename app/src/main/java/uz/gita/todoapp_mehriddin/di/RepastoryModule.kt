package uz.gita.todoapp_mehriddin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastory
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepastoryModule {
    @[Binds Singleton]
    fun bindRepastory(impl: TaskRepastoryImpl): TaskRepastory
}