package uz.gita.todoapp_mehriddin.presentation.add

import uz.gita.todoapp_mehriddin.navigation.AppNavigator
import uz.gita.todoapp_mehriddin.navigation.AppScreen
import javax.inject.Inject

interface AddDiraction {
    suspend fun back()
}

class AddDiractionImpl @Inject constructor(
    private val appNavigator: AppNavigator
):AddDiraction{
    override suspend fun back() {
        appNavigator.back()
    }



}