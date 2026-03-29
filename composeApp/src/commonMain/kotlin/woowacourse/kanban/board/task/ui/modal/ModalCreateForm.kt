package woowacourse.kanban.board.task.ui.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.TaskMockData

@Composable
fun ModalCreateForm(assignee: List<String>, onDismissRequest: () -> Unit, onCreate: (KanbanCard) -> Unit, modifier: Modifier = Modifier) {
    val state = remember { ModalCreateFormState() }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(10.dp),
                ),
        ) {
            ModalHeader(onDismissRequest = onDismissRequest)

            HorizontalDivider(
                thickness = Dp.Hairline,
                color = Color.LightGray,
            )

            ModalBody(
                state = state,
                assignee = assignee,
                modifier = Modifier,
                onDismissRequest = onDismissRequest,
                onCreate = onCreate,
            )
        }
    }
}

@Preview(
    widthDp = 672,
    heightDp = 1000,
)
@Composable
private fun ModalCreateFormPreview() {
    ModalCreateForm(
        assignee = TaskMockData.assignees,
        onDismissRequest = {},
        onCreate = { _ -> },
    )
}
