package uz.gita.todoapp_mehriddin.ui.component

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategoryItem(category: String, modifier: Modifier, color: Color,click:()->Unit) {
    ElevatedButton(
        onClick = click,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text = category, color = Color.White)
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
   // CategoryItem("category", Modifier.padding(horizontal = 8.dp), Color(0xFF979797))
}