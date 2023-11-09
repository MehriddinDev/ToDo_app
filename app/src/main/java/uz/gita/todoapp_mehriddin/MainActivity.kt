package uz.gita.todoapp_mehriddin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.todoapp_mehriddin.navigation.NavigationHandler
import uz.gita.todoapp_mehriddin.presentation.home.HomeScreen
import uz.gita.todoapp_mehriddin.presentation.home.task.TasksScreen
import uz.gita.todoapp_mehriddin.ui.theme.ToDoAppTheme
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                Navigator(screen = HomeScreen()) { navigator ->
                    LaunchedEffect(navigator) {
                        navigationHandler.navigationStack.onEach {
                            it.invoke(navigator)
                        }.launchIn(lifecycleScope)

                    }
                    CurrentScreen()
                }
            }
        }
    }
}