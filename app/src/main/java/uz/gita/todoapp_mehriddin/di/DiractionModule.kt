package uz.gita.todoapp_mehriddin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todoapp_mehriddin.presentation.add.AddDiraction
import uz.gita.todoapp_mehriddin.presentation.add.AddDiractionImpl
import uz.gita.todoapp_mehriddin.presentation.home.task.TaskDiraction
import uz.gita.todoapp_mehriddin.presentation.home.task.TaskDiractionImpl

@Module
@InstallIn(SingletonComponent::class)
interface DiractionModule {

    @Binds
    fun bindTaskDiraction(impl: TaskDiractionImpl): TaskDiraction

    @Binds
    fun bindAddDiraction(impl:AddDiractionImpl):AddDiraction
}