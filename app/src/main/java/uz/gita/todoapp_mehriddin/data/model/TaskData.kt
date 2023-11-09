package uz.gita.todoapp_mehriddin.data.model

import uz.gita.todoapp_mehriddin.data.source.room.entity.TaskEntity

data class TaskData(
    val id: Int,
    val content: String,
    val category: String,
    val date: String,
    val time: String,
    val checkState: Boolean,
    val clickState:Boolean
) {
    fun toEntity() = TaskEntity(id, content, category, date, time, checkState,clickState)
}
