package uz.gita.todoapp_mehriddin.presentation.add

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.todoapp_mehriddin.MyWorker
import uz.gita.todoapp_mehriddin.app.App
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.domain.repastory.TaskRepastory
import uz.gita.todoapp_mehriddin.util.logger
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repastory: TaskRepastory,
    private val diraction: AddDiraction
) : ViewModel(), AddContract.ViewModel {

    override val container =
        container<AddContract.UIState, AddContract.SideEffect>(AddContract.UIState.Init)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEventDispatcher(intent: AddContract.Intent) {
        when (intent) {
            is AddContract.Intent.Add -> {
                repastory.insert(intent.taskData)
                viewModelScope.launch {
                    diraction.back()
                }
                if(intent.chooseDateState){
                    startWorker(intent.taskData)
                }
            }
            AddContract.Intent.Toast -> {
                intent { postSideEffect(AddContract.SideEffect.Toast) }
            }
            AddContract.Intent.Back -> {
                viewModelScope.launch {
                    diraction.back()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startWorker(taskData: TaskData) {
        val dataPart = taskData.date.split("-")
        val timePart = taskData.time.split(":")
        logger("${dataPart[1]}  ${timePart[1]}","FFF")
        val userYear = dataPart[0].toInt()
        val userMonth = dataPart[1].toInt() // Foydalanuvchidan kiritilgan oy (1-12 oralig'ida)
        val userDay = dataPart[2].toInt() // Foydalanuvchidan kiritilgan kun
        val userHour = timePart[0].toInt() // Foydalanuvchidan kiritilgan soat
        val userMinute = timePart[1].toInt()

        val currentDateTime = LocalDateTime.now()

        // Foydalanuvchidan kiritilgan sana va vaqtlarni LocalDateTime obyektiga aylantirish
        val userDateTime = LocalDateTime.of(userYear, userMonth, userDay, userHour, userMinute)

        // Bugungi kundan boshlab tanlangan vaqtgacha qancha muddat qoldigini hisoblash
        val remainingDuration = Duration.between(currentDateTime, userDateTime)
        // Muddatni millisaniyalar sifatida olish
        val remainingMillis = remainingDuration.toMillis()
        logger(remainingDuration.toMinutes().toString() + " = minut","FFF")
        logger(remainingDuration.toMillis().toString()+ " = mills","FFF")

        val oneTime = OneTimeWorkRequestBuilder<MyWorker>()
            .setInitialDelay(remainingMillis, TimeUnit.MILLISECONDS)
            .addTag("ToDo")
            .setInputData(workDataOf("NotificationID" to remainingDuration.toMillis()))
            .build()

        WorkManager.getInstance(App.context).enqueueUniqueWork("uniqeWork",
            ExistingWorkPolicy.REPLACE,oneTime)
    }
}