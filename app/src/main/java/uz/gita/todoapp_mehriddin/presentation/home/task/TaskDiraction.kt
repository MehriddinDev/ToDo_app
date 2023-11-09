package uz.gita.todoapp_mehriddin.presentation.home.task

import uz.gita.todoapp_mehriddin.navigation.AppNavigator
import uz.gita.todoapp_mehriddin.navigation.AppScreen
import javax.inject.Inject

interface TaskDiraction {
    suspend fun navigateTo(screen: AppScreen)
}

class TaskDiractionImpl @Inject constructor(
    private val appNavigator: AppNavigator
):TaskDiraction{
    override suspend fun navigateTo(screen: AppScreen) {
        appNavigator.navigateTo(screen)
    }

}

