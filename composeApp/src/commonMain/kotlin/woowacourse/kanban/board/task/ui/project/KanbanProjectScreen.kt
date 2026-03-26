package woowacourse.kanban.board.task.ui.project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.board.KanbanBoardScreen

@Composable
fun KanbanProjectScreen(modifier: Modifier = Modifier) {
    var selectedBoard by remember { mutableIntStateOf(0) }

    var kanbanProject by remember {
        mutableStateOf(
            KanbanProject(
                projectTitle = "4주차 미션 보드",
            ),
        )
    }

    val kanbanBoard =
        KanbanBoard(title = TaskMockData.boardTitles[selectedBoard], cards = kanbanProject.getKanbanCardByBoardId(selectedBoard))

    Row(
        modifier = modifier,
    ) {
        KanbanProjectSideBar(
            modifier = Modifier.fillMaxHeight(),
            title = kanbanProject.projectTitle,
            boardTitle = TaskMockData.boardTitles,
            selected = selectedBoard,
            onClick = { index ->
                selectedBoard = index
            },
        )
        KanbanBoardScreen(
            boardId = selectedBoard,
            kanbanBoard = kanbanBoard,
            onAddCard = { boardId, form, status ->
                kanbanProject = kanbanProject.addCard(boardId, form, status)
            },
        )
    }
}

@Preview(widthDp = 1500)
@Composable
private fun KanbanProjectScreenPreview() {
    KanbanProjectScreen()
}
