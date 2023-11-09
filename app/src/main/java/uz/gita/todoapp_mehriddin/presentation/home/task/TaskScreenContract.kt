package uz.gita.todoapp_mehriddin.presentation.home.task

import org.orbitmvi.orbit.ContainerHost
import uz.gita.todoapp_mehriddin.data.model.CategoryData
import uz.gita.todoapp_mehriddin.data.model.TaskData

interface TaskScreenContract {
    sealed interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val loading: Boolean = false,
        val categories: List<CategoryData> = emptyList(),
        val tasks: List<TaskData> = emptyList(),
        val lastSelected:String = ""
    )

    sealed interface SideEffect {

    }

    sealed interface Intent {
        object AddScreen : Intent
        data class getTasksByCategory(val categoryName: String) : Intent
        data class Update(val taskData: TaskData) : Intent
        object Init : Intent
        data class Delete(val tasks: List<TaskData>) : Intent
    }
}