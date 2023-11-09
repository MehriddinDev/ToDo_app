package uz.gita.todoapp_mehriddin.presentation.home.task

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.todoapp_mehriddin.R
import uz.gita.todoapp_mehriddin.data.model.CategoryData
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.ui.component.CategoryItem
import uz.gita.todoapp_mehriddin.ui.component.TaskItemUndone
import uz.gita.todoapp_mehriddin.util.logger

object TasksScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(R.drawable.tasks)

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
        val viewModel: TaskScreenContract.ViewModel = getViewModel<TaskViewModel>()
        val uiState = viewModel.collectAsState()

        //viewModel::onEventDispatcher.invoke(TaskScreenContract.Intent.Init)

        TasksScreenContent(uiState = uiState, eventDispatcher = viewModel::onEventDispatcher)
        val context = LocalContext.current

/*
            if (!(ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED)
            ) {

            }*/

    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun TasksScreenContent(
    uiState: State<TaskScreenContract.UIState>,
    eventDispatcher: (TaskScreenContract.Intent) -> Unit
) {
    var categories by remember { mutableStateOf(listOf<CategoryData>()) }
    categories = uiState.value.categories
    var tasks by remember { mutableStateOf(listOf<TaskData>()) }
    tasks = uiState.value.tasks
    var isLongClick by remember { mutableStateOf(false) }

    var clickedCategory by remember {mutableStateOf(uiState.value.lastSelected)}
    clickedCategory = uiState.value.lastSelected

    Box(
        Modifier
            .background(colorResource(id = R.color.dark_blue))
            .fillMaxSize()
    ) {
        Column {

            LazyRow(Modifier.padding(top = 4.dp)) {
                items(categories) { category ->
                    logger(category.name, "CCC")
                    CategoryItem(
                        category = category.name,
                        modifier = Modifier.padding(horizontal = 4.dp),
                        color = colorResource(
                            id = if (category.name == clickedCategory) {
                                R.color.light_blue2
                            } else {
                                R.color.light_blue
                            }
                        )
                    ) {
                        clickedCategory = category.name
                        eventDispatcher.invoke(
                            TaskScreenContract.Intent.getTasksByCategory(
                                category.name
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            if(uiState.value.tasks.isNotEmpty()){
                LazyColumn {
                    items(tasks) { task ->
                        logger("lazyColumn = ${task.content}", "CCC")
                        TaskItemUndone(
                            isLongClick = task.clickState,
                            state = false,
                            content = task.content,
                            time = task.time,
                            clickCheck = {
                                eventDispatcher.invoke(TaskScreenContract.Intent.Update(task))
                            },
                            longClick = {
                                logger("onLongClick = ${tasks}", "LKMN")
                                logger("onLongClick = ${isLongClick}", "LKMN")
                                if (!isLongClick) {
                                    isLongClick = true
                                    tasks = tasks.map {
                                        if (it == task) {
                                            it.copy(clickState = !task.clickState)
                                        } else it
                                    }
                                }
                            },
                            onClick = {
                                logger("onClick = ${tasks}", "LKMN")
                                if (isLongClick) {
                                    tasks = tasks.map {
                                        if (it == task) {
                                            it.copy(clickState = !task.clickState)
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
                    Image(painter = painterResource(id = R.drawable.placeholder), contentDescription = "", colorFilter = ColorFilter.tint(Color.Gray), modifier = Modifier.size(150.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Nothing to do", color = Color.Gray, fontSize = 16.sp)
                }
            }

        }



        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                if (!isLongClick) eventDispatcher.invoke(TaskScreenContract.Intent.AddScreen) else eventDispatcher.invoke(
                    TaskScreenContract.Intent.Delete(tasks.filter { it.clickState })
                )

                isLongClick = false
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(28.dp),
        ) {
            if (!isLongClick) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add FAB",
                    tint = colorResource(id = R.color.light_blue),
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Add FAB",
                    tint = colorResource(id = R.color.light_blue),
                )
            }
        }
    }
}