package woowacourse.kanban.board.task.ui.project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.board.KanbanBoardScreen

@Composable
fun KanbanProjectScreen(modifier: Modifier = Modifier) {
    var draggedTask by remember { mutableStateOf<KanbanCard?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<KanbanStatus, Rect>() }

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
            getIsDropTarget = { status ->
                currentDragPosition?.let { columnBounds[status]?.contains(it) } ?: false
            },
            onBoundsChanged = { status, rect -> columnBounds[status] = rect },
            onTaskDragStart = { task -> draggedTask = task },
            onTaskDragChange = { pos -> currentDragPosition = pos },
            onTaskDragEnd = {
                val dropPosition = currentDragPosition ?: return@KanbanBoardScreen
                val targetStatus = columnBounds.entries
                    .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                draggedTask?.let { task ->
                    if (targetStatus != null && task.status != targetStatus) {
                        kanbanProject = kanbanProject.updateCardStatus(task.id, targetStatus)
                    }
                }
                currentDragPosition = null
                draggedTask = null
            },
            onTaskDragCancel = {
                currentDragPosition = null
                draggedTask = null
            },
        )
    }
}

@Preview(widthDp = 1500)
@Composable
private fun KanbanProjectScreenPreview() {
    KanbanProjectScreen()
}
