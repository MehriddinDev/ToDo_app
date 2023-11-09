package uz.gita.todoapp_mehriddin.presentation.home.done

import android.view.WindowInsets.Side
import org.orbitmvi.orbit.ContainerHost
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.presentation.home.task.TaskScreenContract

interface DoneContract {
    sealed interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }
    data class UIState(
        val tasks:List<TaskData> = emptyList(),
        val loading:Boolean = false
        )
    sealed interface SideEffect{

    }
    sealed interface Intent{
        data class Update(val taskData: TaskData):Intent
        data class Delete(val tasks:List<TaskData>):  Intent
    }
}