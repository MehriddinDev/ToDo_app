package uz.gita.todoapp_mehriddin.presentation.home.done

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastory
import uz.gita.todoapp_mehriddin.util.logger
import javax.inject.Inject

@HiltViewModel
class DoneViewModel @Inject constructor(
    private val repastory: TaskRepastory
) : DoneContract.ViewModel, ViewModel() {


    override val container =
        container<DoneContract.UIState, DoneContract.SideEffect>(DoneContract.UIState())

    override fun onEventDispatcher(intent: DoneContract.Intent) {
        when(intent){
            is DoneContract.Intent.Update -> {
                repastory.update(intent.taskData,false)
            }
            is DoneContract.Intent.Delete -> {
                repastory.deleteSelectedTasks(intent.tasks)

                repastory.getAllTasksDone().onEach {
                    intent { reduce { state.copy(tasks = it) } }
                }.launchIn(viewModelScope)
            }
        }
    }

    init {
        repastory.getAllTasksDone().onEach {
            intent { reduce { state.copy(tasks = it) } }
        }.launchIn(viewModelScope)
    }

}