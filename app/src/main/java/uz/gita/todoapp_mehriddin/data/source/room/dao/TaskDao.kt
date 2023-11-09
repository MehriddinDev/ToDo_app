package uz.gita.todoapp_mehriddin.data.source.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.todoapp_mehriddin.data.source.room.entity.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE checkState = 0")
    fun getAllTasksUnDone(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE checkState = 1")
    fun getAllTasksDone(): Flow<List<TaskEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskEntity: TaskEntity)
    
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(taskEntity: TaskEntity)

    @Query("SELECT * FROM tasks WHERE category = :name")
    fun getTasksByCategory(name:String): List<TaskEntity>

    @Delete
    fun deleteSelected(list:List<TaskEntity>)

}