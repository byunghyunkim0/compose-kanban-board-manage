package woowacourse.kanban.board

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.modal.RememberModalCreateFormState
import woowacourse.kanban.board.task.ui.project.KanbanProjectScreen

@Composable
@Preview(widthDp = 1300, heightDp = 900)
fun App() {
    MaterialTheme {
        val modalCreateFormState = RememberModalCreateFormState(TaskMockData.assignees)
        KanbanProjectScreen(modalCreateFormState = modalCreateFormState)
    }
}
