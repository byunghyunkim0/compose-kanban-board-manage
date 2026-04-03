package woowacourse.kanban.board.task.ui.project

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.board.task.domain.KanbanBoard
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanProject
import woowacourse.kanban.board.task.domain.KanbanProjectResult
import woowacourse.kanban.board.task.domain.KanbanStatus

@Composable
fun RememberKanbanProjectState(coroutineScope: CoroutineScope, kanbanProject: KanbanProject): KanbanProjectState {
    val snackbarHostState = remember { SnackbarHostState() }
    return remember(kanbanProject) {
        KanbanProjectState(
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            kanbanProject = kanbanProject,
        )
    }
}

@Stable
class KanbanProjectState(val snackbarHostState: SnackbarHostState, val coroutineScope: CoroutineScope, kanbanProject: KanbanProject) {
    var selectedBoard by mutableIntStateOf(0)

    var kanbanProject by mutableStateOf(
        kanbanProject,
    )

    val kanbanBoard: KanbanBoard?
        get() = kanbanProject.getBoard(selectedBoard)

    var draggedTask by mutableStateOf<KanbanCard?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<KanbanStatus, Rect>()

    var isShowModal by mutableStateOf(false)

    fun onSelectBoard(index: Int) {
        selectedBoard = index
    }

    fun onAddCard(boardId: Int, card: KanbanCard) {
        val newProject = kanbanProject.addBoardCard(
            boardId,
            card,
        )
        if (newProject is KanbanProjectResult.Success) kanbanProject = newProject.project
    }

    fun onCreate(card: KanbanCard) {
        onAddCard(selectedBoard, card)
        isShowModal = false
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = "새로운 태스크가 추가되었습니다.",
                duration = SnackbarDuration.Short,
            )
        }
    }

    fun onUpdateStatus(card: KanbanCard, targetStatus: KanbanStatus) {
        val updateProject = kanbanProject.updateCardStatus(
            boardId = selectedBoard,
            cardId = card.id,
            status = targetStatus,
        )
        if (updateProject is KanbanProjectResult.Success) kanbanProject = updateProject.project
    }
}
