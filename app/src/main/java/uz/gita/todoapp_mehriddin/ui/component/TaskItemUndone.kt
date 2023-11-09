package uz.gita.todoapp_mehriddin.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.todoapp_mehriddin.R
import uz.gita.todoapp_mehriddin.data.source.sharedPref.MyPref

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun TaskItemUndone(
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
                    Spacer(modifier = Modifier.height(6.dp))
                }

            }
        }

    }
}