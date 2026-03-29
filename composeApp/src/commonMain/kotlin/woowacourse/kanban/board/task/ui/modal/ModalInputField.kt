package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModalInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    maxLines: Int,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    supportingMessage: String? = null,
    errorMessage: String? = null,
) {
    val color = if (isValid) Color.Black else Color.Red
    val message = (errorMessage ?: supportingMessage)
    val text = message ?: ""
    Column(
        modifier = modifier,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(width = 1.dp, color = color, shape = RoundedCornerShape(10.dp)),
            value = value,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = color,
                unfocusedTextColor = color,
            ),
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeHolder,
                    color = color,
                )
            },
            maxLines = maxLines,
        )

        Text(
            modifier = Modifier.padding(
                top = 4.dp, start = 16.dp, end = 16.dp,
            ),
            text = text,
            fontSize = 12.sp,
            color = color,
        )
    }
}
