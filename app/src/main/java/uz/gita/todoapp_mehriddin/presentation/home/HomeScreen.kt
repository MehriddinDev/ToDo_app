package uz.gita.todoapp_mehriddin.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.todoapp_mehriddin.R
import uz.gita.todoapp_mehriddin.navigation.AppScreen
import uz.gita.todoapp_mehriddin.presentation.home.done.DoneScreen
import uz.gita.todoapp_mehriddin.presentation.home.task.TasksScreen
import uz.gita.todoapp_mehriddin.util.logger

class HomeScreen : AppScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(TasksScreen) {
            Scaffold(topBar = {
                NavigationBar(
                    modifier = Modifier
                        .height(56.dp)
                        .background(colorResource(id = R.color.light_blue)),
                    containerColor = colorResource(id = R.color.light_blue),
                    tonalElevation = 10.dp,
                ) {
                    TabNavigationItem(tab = TasksScreen)
                    TabNavigationItem(tab = DoneScreen)
                }
            },
                content = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        CurrentTab()
                    }
                })
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            Icon(
                painter = tab.options.icon!!,
                contentDescription = tab.options.title,
                Modifier.size(24.dp),
                 if (tabNavigator.current == tab)colorResource(id = R.color.dark_blue) else Color.White,
            )
        }, modifier = Modifier, alwaysShowLabel = false
    )
}
