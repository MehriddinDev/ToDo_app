package uz.gita.todoapp_mehriddin.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.todoapp_mehriddin.data.source.room.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert
    fun insert(categoryEntity: List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun getAllCategory(): Flow<List<CategoryEntity>>
}