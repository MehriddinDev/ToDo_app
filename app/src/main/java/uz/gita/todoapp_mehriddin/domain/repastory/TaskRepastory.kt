package uz.gita.todoapp_mehriddin.domain.repastory

import kotlinx.coroutines.flow.Flow
import uz.gita.todoapp_mehriddin.data.model.CategoryData
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.data.source.room.entity.CategoryEntity

interface TaskRepastory {
    fun getAllTasksUnDone(): Flow<List<TaskData>>
    fun getAllTasksDone(): Flow<List<TaskData>>
    fun getCategories(): Flow<List<CategoryEntity>>
    fun insert(taskData: TaskData)
    fun getTasksByCategory(categoryName: String): List<TaskData>
    fun update(taskData: TaskData,state:Boolean)
    fun deleteSelectedTasks(tasks:List<TaskData>)

    fun saveLastSelectedCategory(name:String)
    fun getLastSelectedCategory():Flow<String>
}