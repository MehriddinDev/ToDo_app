package uz.gita.todoapp_mehriddin.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.todoapp_mehriddin.data.model.TaskData

@Entity("tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val content:String,
    val category:String,
    val date:String,
    val time:String,
    val checkState:Boolean,
    val clickState:Boolean
){
    fun toData()= TaskData(id, content, category, date,time, checkState,clickState)
}