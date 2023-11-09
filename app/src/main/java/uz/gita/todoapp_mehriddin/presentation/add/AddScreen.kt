package uz.gita.todoapp_mehriddin.presentation.add

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.todoapp_mehriddin.R
import uz.gita.todoapp_mehriddin.data.model.TaskData
import uz.gita.todoapp_mehriddin.navigation.AppScreen
import uz.gita.todoapp_mehriddin.util.logger
import uz.gita.todoapp_mehriddin.util.toast

class AddScreen() : AppScreen() {
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel:AddContract.ViewModel = getViewModel<AddViewModel>()
        val uiState = viewModel.collectAsState().value
        val sideEffect = viewModel.collectSideEffect{sideEffect->
            when(sideEffect){
                AddContract.SideEffect.Toast -> {
                    toast(context,"vazifa kiriting")
                }
            }
        }
        AddScreenContent(uiState,viewModel::onEventDispatcher)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(
    uiState:AddContract.UIState,
    eventDispatcher:(AddContract.Intent)->Unit
) {
    var content by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stateCategoryMenu by remember { mutableStateOf(false) }

    val calendarState = rememberSheetState()
    val clockState = rememberSheetState()
    CalendarDialog(
        state = calendarState, selection = CalendarSelection.Date {
            date = it.toString()
        }, config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        )
    )

    ClockDialog(state = clockState, selection = ClockSelection.HoursMinutes { hours, minuts ->
        val h = if (hours < 10) "0$hours" else "$hours"
        val m = if (minuts < 10) "0$minuts" else "$minuts"
        time = "$h:$m"
    })


    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.dark_blue))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                elevation = CardDefaults.cardElevation(14.dp),
                shape = AbsoluteCutCornerShape(0.dp),
                colors = CardDefaults.cardColors(colorResource(id = R.color.light_blue))
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        Icons.Default.ArrowBack,
                        "", tint = Color.White, modifier = Modifier.clickable {
                            eventDispatcher.invoke(AddContract.Intent.Back)
                        }
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Vazifa qo'shish",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                }
            }

            Column(Modifier.padding(horizontal = 14.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Nima qilishingiz kerak ?",
                    color = colorResource(id = R.color.text_blue),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                val customTextStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textDecoration = null
                )
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    Modifier
                        .background(colorResource(id = R.color.dark_blue))
                        .fillMaxWidth()
                        .wrapContentWidth()
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.light_blue)
                        )
                        .padding(end = 8.dp),
                    placeholder = {
                        Text(
                            text = "vazifani kiriting ...",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    },
                    textStyle = customTextStyle,
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = colorResource(id = R.color.dark_blue),
                        cursorColor = colorResource(
                            id = R.color.text_blue
                        ),
                        textColor = Color.White
                    )
                )

                Spacer(Modifier.height(70.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Spacer(Modifier.width(10.dp))
                    FloatingActionButton(
                        onClick = { calendarState.show() },
                        modifier = Modifier
                            .size(38.dp)
                            .padding(end = 4.dp),
                        containerColor = colorResource(id = R.color.text_blue),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(
                                Color.White
                            ), modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = "   Sana",
                        color = colorResource(id = R.color.text_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = date,
                        color = colorResource(id = R.color.text_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(colorResource(id = R.color.light_blue))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {

                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Spacer(Modifier.width(10.dp))
                    FloatingActionButton(
                        onClick = { clockState.show() },
                        modifier = Modifier
                            .size(38.dp)
                            .padding(end = 4.dp),
                        containerColor = colorResource(id = R.color.text_blue),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(
                                Color.White
                            ), modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = "   Vaqt",
                        color = colorResource(id = R.color.text_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = time,
                        color = colorResource(id = R.color.text_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(colorResource(id = R.color.light_blue))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {}
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    Spacer(Modifier.width(10.dp))
                    FloatingActionButton(
                        onClick = { stateCategoryMenu = true },
                        modifier = Modifier
                            .size(38.dp)
                            .padding(end = 4.dp),
                        containerColor = colorResource(id = R.color.text_blue),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.category),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(
                                Color.White
                            ), modifier = Modifier.padding(4.dp)
                        )
                    }
                    Text(
                        text = "   Toifa",
                        color = colorResource(id = R.color.text_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                    DropdownMenu(
                        expanded = stateCategoryMenu,
                        onDismissRequest = { stateCategoryMenu = false },
                        modifier = Modifier
                            .border(
                                1.dp,
                                colorResource(id = R.color.white),
                                shape = RoundedCornerShape(0.dp)
                            )

                            .background(colorResource(id = R.color.light_blue))// Kerakli o'lchamni berishingiz mumkin
                    ) {
                        Column() {
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp)
                                    .clickable {
                                        stateCategoryMenu = false
                                        category = "Shaxsiy"
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Shaxsiy",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp)
                                    .clickable {
                                        stateCategoryMenu = false
                                        category = "Ish"
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ish",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp)
                                    .clickable {
                                        stateCategoryMenu = false
                                        category = "Oilaviy"
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Oilaviy",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp)
                                    .clickable {
                                        stateCategoryMenu = false
                                        category = "O'qish"
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "O'qish",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp)
                                    .clickable {
                                        stateCategoryMenu = false
                                        category = "Do'stlar"
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Do'stlar",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(150.dp)
                                    .clickable {
                                        stateCategoryMenu = false
                                        category = "Do'kon"
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Do'kon",
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = category,
                        color = colorResource(id = R.color.text_blue),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }

        FloatingActionButton(
            onClick = {
                if(content == ""){
                    eventDispatcher.invoke(AddContract.Intent.Toast)
                }else{
                    eventDispatcher.invoke(AddContract.Intent.Add(TaskData(0,content,category,date,time,false,false),if(time == "" || date == "") false else true))
                }
            },
            Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
            containerColor = Color.White,
            shape = RoundedCornerShape(28.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Done,
                contentDescription = "Add FAB",
                tint = colorResource(id = R.color.light_blue),
            )
        }
    }
}

private fun getCategories(): List<String> {
    return listOf("Barchasi", "Shaxsiy", "Ish", "Oilaviy", "Maktab", "Do'kon")
}

@Preview
@Composable
fun AddScreenPrev() {
   // AddScreenContent()
}

