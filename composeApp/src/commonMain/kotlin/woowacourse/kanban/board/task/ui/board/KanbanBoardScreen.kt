package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskMockData
import woowacourse.kanban.board.task.ui.modal.ModalCreateForm
import woowacourse.kanban.board.theme.BoardBackground
import woowacourse.kanban.board.theme.SnackBarBackground

@Composable
fun KanbanBoardScreen(
    boardId: Int,
    kanbanBoard: KanbanBoard,
    onAddCard: (Int, KanbanCard) -> Unit,
    modifier: Modifier = Modifier,
    getIsDropTarget: (KanbanStatus) -> Boolean = { false },
    onBoundsChanged: (KanbanStatus, Rect) -> Unit = { _, _ -> },
    onTaskDragStart: (KanbanCard) -> Unit = {},
    onTaskDragChange: (Offset) -> Unit = {},
    onTaskDragEnd: () -> Unit = {},
    onTaskDragCancel: () -> Unit = {},
) {
    var isShowModal by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (isShowModal) {
        ModalCreateForm(
            assignee = TaskMockData.assignees,
            onDismissRequest = { isShowModal = false },
            onCreate = { card ->
                onAddCard(boardId, card)
                isShowModal = false
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "새로운 태스크가 추가되었습니다.",
                        duration = SnackbarDuration.Short,
                    )
                }
            },
            modifier = Modifier.width(672.dp).height(820.dp),
        )
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                SnackBarCard(
                    modifier = Modifier,
                    message = snackbarData.visuals.message,
                    onDismiss = { snackbarData.dismiss() },
                )
            }
        },
        topBar = {
            KanbanBoardHeader(
                modifier = Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 24.dp,
                ),
                title = kanbanBoard.title,
                doneCount = kanbanBoard.doneCount,
                totalCount = kanbanBoard.totalCount,
                onCreateClick = { isShowModal = true },
            )
        },
    ) { paddingValues ->
        KanbanBody(
            kanbanBoard = kanbanBoard,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .background(BoardBackground)
                .padding(24.dp),
            getIsDropTarget = getIsDropTarget,
            onBoundsChanged = onBoundsChanged,
            onTaskDragStart = onTaskDragStart,
            onTaskDragChange = onTaskDragChange,
            onTaskDragEnd = onTaskDragEnd,
            onTaskDragCancel = onTaskDragCancel,
        )
    }
}

@Composable
private fun SnackBarCard(message: String, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .width(344.dp)
            .height(48.dp)
            .background(
                color = SnackBarBackground,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color.White,
        )

        Icon(
            imageVector = Icons.Default.Close,
            modifier = modifier.size(24.dp).clickable(onClick = { onDismiss() }),
            contentDescription = "스낵바 닫기",
            tint = Color.White,
        )
    }
}

@Preview(
    widthDp = 1300,
    heightDp = 900,
)
@Composable
private fun KanbanBoardScreenPreview() {
    KanbanBoardScreen(
        boardId = 0,
        onAddCard = { _, _ -> },
        kanbanBoard = KanbanBoard(
            title = "compose",
            cards = listOf(),
            boardId = 0,
        ),
    )
}
