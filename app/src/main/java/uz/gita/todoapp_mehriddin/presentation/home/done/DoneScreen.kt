package uz.gita.todoapp_mehriddin.presentation.home.done

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.todoapp_mehriddin.R
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.ui.component.TaskItemUndone
import uz.gita.todoapp_mehriddin.util.logger

object DoneScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(R.drawable.done2)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: DoneContract.ViewModel = getViewModel<DoneViewModel>()
        val uiState = viewModel.collectAsState()
        DoneScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
    }
}

@Composable
fun DoneScreenContent(
    uiState: State<DoneContract.UIState>,
    eventDispatcher: (DoneContract.Intent) -> Unit
) {
    var tasks by remember { mutableStateOf(listOf<TaskData>()) }
    var isLongClick by remember { mutableStateOf(false) }

    tasks = uiState.value.tasks

    Box(
        Modifier
            .background(colorResource(id = R.color.dark_blue))
            .fillMaxSize()
    ) {
        if (uiState.value.tasks.isNotEmpty()){
            LazyColumn {
                items(tasks) { task ->
                    logger("tasks: ${task.clickState}", "LKMN")
                    TaskItemUndone(
                        isLongClick = task.clickState,
                        state = true,
                        content = task.content,
                        time = task.time,
                        clickCheck = {
                            eventDispatcher.invoke(DoneContract.Intent.Update(task))
                        },
                        longClick = {
                            logger("onLongClick = ${tasks}", "LKMN")

                            if (!isLongClick) {
                                isLongClick = true
                                tasks = tasks.map {
                                    if (it == task) {
                                        logger("kirdi onLongClick = ${it}", "LKMN")
                                        it.copy(clickState =true)
                                    } else it
                                }
                                logger("${tasks}", "LKMN")
                            }
                        },
                        onClick = {
                            logger("onClick = ${tasks}", "LKMN")
                            if (isLongClick) {
                                tasks = tasks.map {
                                    if (it == task) {
                                        task.copy(clickState = !task.clickState)
                                    } else it
                                }
                                var bool = true
                                tasks.forEach {
                                    if (it.clickState) {
                                        bool = false
                                    }
                                }
                                if (bool) {
                                    isLongClick = false
                                }

                            }
                        })
                }
            }
        }else{
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Spacer(modifier = Modifier.height(170.dp))
                Text(text = "No finished task", color = Color.Gray, fontSize = 16.sp)
            }
        }

        if (isLongClick) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    eventDispatcher.invoke(DoneContract.Intent.Delete(tasks.filter { it.clickState }))
                    isLongClick = false
                },
                containerColor = Color.White,
                shape = RoundedCornerShape(28.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Add FAB",
                    tint = colorResource(id = R.color.light_blue),
                )
            }
        }
    }
}
