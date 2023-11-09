package uz.gita.todoapp_mehriddin.presentation.home.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.todoapp_mehriddin.data.model.CategoryData
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastory
import uz.gita.todoapp_mehriddin.presentation.add.AddScreen
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repastory: TaskRepastory,
    private val diraction: TaskDiraction
) : TaskScreenContract.ViewModel, ViewModel() {

    override val container = container<TaskScreenContract.UIState, TaskScreenContract.SideEffect>(
        TaskScreenContract.UIState()
    )

    init {
        init()
    }

    fun init() {

        intent {
            reduce {
                state.copy(categories = getCategory())
            }
        }
        //viewModelScope.launch { delay(1000) }

        repastory.getAllTasksUnDone().onEach { result ->
            intent {
                reduce {
                    state.copy(tasks = result)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: TaskScreenContract.Intent) {
        when (intent) {
            TaskScreenContract.Intent.AddScreen -> {
                viewModelScope.launch {
                    diraction.navigateTo(AddScreen())
                }
            }

            is TaskScreenContract.Intent.getTasksByCategory -> {
                repastory.saveLastSelectedCategory(intent.categoryName)

                repastory.getLastSelectedCategory().onEach {
                    intent { reduce { state.copy(lastSelected = it) } }
                }.launchIn(viewModelScope)
                if (intent.categoryName == "Barchasi") {
                    repastory.getAllTasksUnDone().onEach {
                        intent { reduce { state.copy(tasks = it) } }
                    }.launchIn(viewModelScope)
                } else {
                    val tasks = repastory.getTasksByCategory(intent.categoryName)
                    intent { reduce { state.copy(tasks = tasks) } }
                }

            }
            is TaskScreenContract.Intent.Update -> {
                repastory.update(intent.taskData, true)
            }
            TaskScreenContract.Intent.Init -> {
                init()
            }
            is TaskScreenContract.Intent.Delete -> {
                repastory.deleteSelectedTasks(intent.tasks)
                init()
            }
        }
    }

    private fun getCategory(): List<CategoryData> {
        return listOf(
            CategoryData("Barchasi"), CategoryData("Shaxsiy"),
            CategoryData("Ish"),
            CategoryData("Oilaviy"), CategoryData("O'qish"),
            CategoryData("Do'kon"),
            CategoryData("Do'stlar")
        )
    }


}
