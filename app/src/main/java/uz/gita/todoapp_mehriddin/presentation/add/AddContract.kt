package uz.gita.todoapp_mehriddin.presentation.add

import org.orbitmvi.orbit.ContainerHost
import uz.gita.todoapp_mehriddin.data.model.TaskData

interface AddContract {
    sealed interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }
    sealed interface UIState{
        object Init:UIState
    }
    sealed interface SideEffect{
        object Toast:SideEffect
    }
    sealed interface Intent{
        data class Add(val taskData: TaskData, val chooseDateState:Boolean):Intent
        object Toast:Intent
        object Back:Intent
    }
}