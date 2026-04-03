package woowacourse.kanban.board.task.ui.project

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.board.KanbanBoardScreen
import woowacourse.kanban.board.task.ui.modal.ModalCreateFormState
import woowacourse.kanban.board.task.ui.modal.RememberModalCreateFormState

@Composable
fun KanbanProjectScreen(modalCreateFormState: ModalCreateFormState, kanbanProjectState: KanbanProjectState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
    ) {
        KanbanProjectSideBar(
            modifier = Modifier.fillMaxHeight(),
            title = kanbanProjectState.kanbanProject.projectTitle,
            boardTitle = kanbanProjectState.kanbanProject.boardTitles,
            selected = kanbanProjectState.selectedBoard,
            onClick = { index ->
                kanbanProjectState.onSelectBoard(index)
            },
        )
        KanbanBoardScreen(
            modalCreateFormState = modalCreateFormState,
            kanbanProjectState = kanbanProjectState,
            getIsDropTarget = { status ->
                kanbanProjectState.currentDragPosition?.let { kanbanProjectState.columnBounds[status]?.contains(it) } ?: false
            },
            onBoundsChanged = { status, rect -> kanbanProjectState.columnBounds[status] = rect },
            onTaskDragStart = { task -> kanbanProjectState.draggedTask = task },
            onTaskDragChange = { pos -> kanbanProjectState.currentDragPosition = pos },
            onTaskDragEnd = {
                val dropPosition = kanbanProjectState.currentDragPosition ?: return@KanbanBoardScreen
                val targetStatus = kanbanProjectState.columnBounds.entries
                    .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                kanbanProjectState.draggedTask?.let { task ->
                    if (targetStatus != null && task.status != targetStatus) {
                        kanbanProjectState.onUpdateStatus(
                            card = task,
                            targetStatus = targetStatus,
                        )
                    }
                }
                kanbanProjectState.currentDragPosition = null
                kanbanProjectState.draggedTask = null
            },
            onTaskDragCancel = {
                kanbanProjectState.currentDragPosition = null
                kanbanProjectState.draggedTask = null
            },
        )
    }
}

@Preview(widthDp = 1500)
@Composable
private fun KanbanProjectScreenPreview() {
    KanbanProjectScreen(
        modalCreateFormState = RememberModalCreateFormState(TaskMockData.assignees),
        kanbanProjectState = RememberKanbanProjectState(
            coroutineScope = rememberCoroutineScope(),
            kanbanProject = KanbanProject(
                projectTitle = "4주차 미션 보드",
                boards = TaskMockData.boards,
            ),
        ),
    )
}
