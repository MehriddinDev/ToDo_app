package uz.gita.todoapp_mehriddin.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.todoapp_mehriddin.R
import uz.gita.todoapp_mehriddin.ui.theme.ToDoAppTheme
import uz.gita.todoapp_mehriddin.util.logger

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun TaskItemDone(
    isLongClick: Boolean,
    state: Boolean,
    content: String,
    time: String,
    clickCheck: (Boolean) -> Unit,
    onClick:()->Unit,
    longClick:()->Unit
) {

    var isChecked by remember { mutableStateOf(state) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .animateContentSize()
            .combinedClickable(onClick = {
                onClick.invoke()
            }, onLongClick = {
                longClick.invoke()
            }),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = if(isLongClick) R.color.light_blue else R.color.card_blue),
        ),
        border = BorderStroke(
            if (!isLongClick) 1.2.dp else 3.dp,
            colorResource(id = if (!isLongClick) R.color.light_blue else R.color.white)
        ),

        ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(start = 6.dp)

        ) {
            Checkbox(
                checked = isChecked, onCheckedChange = {
                    //shu yerini qara

                    clickCheck.invoke(it)
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = colorResource(id = R.color.checkbox_wall_blue),
                    checkedColor = colorResource(
                        id = R.color.checkbox_blue
                    )
                )
            )

            Column(Modifier.weight(1f)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = content, fontSize = 18.sp, color = Color.White)

                if(time != ""){
                    Text(
                        text = time,
                        fontSize = 14.sp,
                        color = Color.Red
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

            }
        }

    }
}
//    isLongClick: Boolean,
//    state: Boolean,
//    content: String,
//    time: String,
//    category: String,
//    clickCheck: (Boolean) -> Unit,
//    onClick:()->Unit,
//    longClick:()->Unit
//) {
//
//    var isChecked by remember { mutableStateOf(state) }
//    var isLongClick by remember { mutableStateOf(false) }
//
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 8.dp, vertical = 4.dp)
//            .combinedClickable(onClick = {
//
//            }, onLongClick = {
//                isLongClick = !isLongClick
//            }),
//
//        colors = CardDefaults.cardColors(
//            containerColor = colorResource(id = R.color.card_blue),
//        ),
//        border = BorderStroke(
//            if (isChecked) 1.2.dp else 3.dp,
//            colorResource(id = if (isChecked) R.color.light_blue else R.color.white)
//        )
//
//    ) {
//        Row(
//            verticalAlignment = Alignment.Top,
//            modifier = Modifier.padding(start = 6.dp)
//        ) {
//            Checkbox(
//                checked = isChecked, onCheckedChange = {
//                    isChecked = it
//                    clickCheck.invoke(it)
//                },
//                colors = CheckboxDefaults.colors(
//                    uncheckedColor = colorResource(id = R.color.checkbox_wall_blue),
//                    checkedColor = colorResource(
//                        id = R.color.checkbox_blue
//                    )
//                )
//            )
//
//            Column(Modifier.weight(1f)) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = content, fontSize = 18.sp, color = Color.White)
//                if (time != ""){
//                    Text(
//                        text = time,
//                        fontSize = 14.sp,
//                        color = colorResource(id = R.color.text_blue)
//                    )
//                }
//
//                if(category != ""){
//                    Text(text = category, fontSize = 14.sp, color = Color.White)
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//
//            }
//        }
//    }
//
//
//}
