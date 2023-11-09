package uz.gita.todoapp_mehriddin.domain.repastory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import uz.gita.todoapp_mehriddin.data.model.CategoryData
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.data.source.room.dao.CategoryDao
import uz.gita.todoapp_mehriddin.data.source.room.dao.TaskDao
import uz.gita.todoapp_mehriddin.data.source.room.entity.CategoryEntity
import uz.gita.todoapp_mehriddin.data.source.room.entity.TaskEntity
import uz.gita.todoapp_mehriddin.data.source.sharedPref.MyPref
import javax.inject.Inject

class TaskRepastoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val categroyDao:CategoryDao,
    private val pref:MyPref
) : TaskRepastory {
    override fun getAllTasksUnDone(): Flow<List<TaskData>> =
        taskDao.getAllTasksUnDone().map { list ->
            list.map { it.toData() }
        }

    override fun getAllTasksDone(): Flow<List<TaskData>> =
        taskDao.getAllTasksDone().map{ list->
            list.map { it.toData() }
        }

    override fun getCategories(): Flow<List<CategoryEntity>> = categroyDao.getAllCategory()


    override fun insert(taskData: TaskData) {
        taskDao.insert(taskData.toEntity())
    }

    override fun getTasksByCategory(categoryName: String): List<TaskData> =
        taskDao.getTasksByCategory(categoryName).map { taskData ->
            taskData.toData()
        }

    override fun update(taskData: TaskData,state:Boolean) {
        taskDao.update(TaskEntity(taskData.id,taskData.content,taskData.category,taskData.date,taskData.time,state,false))
    }

    override fun deleteSelectedTasks(tasks: List<TaskData>) {
        taskDao.deleteSelected(tasks.map {
            it.toEntity()
        })
    }

    init {
        if (pref.getFirst()){
            categroyDao.insert(getCategory())
           pref.saveFirst(false)
        }
    }

    private fun getCategory(): List<CategoryEntity> {
        return listOf(CategoryEntity("Barchasi"), CategoryEntity("Shaxsiy"),
            CategoryEntity("Ish"),
            CategoryEntity( "Oilaviy"), CategoryEntity("O'qish"),
            CategoryEntity( "Do'kon"),
            CategoryEntity( "Do'stlar"))
    }

    override fun saveLastSelectedCategory(name: String) {
        pref.saveLastCategory(name)
    }

    override fun getLastSelectedCategory(): Flow<String> {
        return flow { emit(pref.getLastCategory()) }.flowOn(Dispatchers.IO)
    }
}