package uz.gita.todoapp_mehriddin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastory
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastoryImpl
import uz.gita.todoapp_mehriddin.navigation.AppNavigator
import uz.gita.todoapp_mehriddin.navigation.NavigationDispatcher
import uz.gita.todoapp_mehriddin.navigation.NavigationHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(impl: NavigationDispatcher): NavigationHandler


}


