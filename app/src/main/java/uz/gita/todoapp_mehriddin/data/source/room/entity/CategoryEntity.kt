package uz.gita.todoapp_mehriddin.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.todoapp_mehriddin.data.model.CategoryData

@Entity("category")
data class CategoryEntity(
    @PrimaryKey
    val name:String
){
    fun toData()=CategoryData(name)
}
