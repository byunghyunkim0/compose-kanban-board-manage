package woowacourse.kanban.board.task.domain

sealed class KanbanCardResult {
    data class Success(val card: KanbanCard) : KanbanCardResult()
    data class Failure(val error: KanbanError) : KanbanCardResult()
}

sealed class KanbanBoardResult {
    data class Success(val board: KanbanBoard) : KanbanBoardResult()
    data class Failure(val error: KanbanError) : KanbanBoardResult()
}

sealed class KanbanProjectResult {
    data class Success(val project: KanbanProject) : KanbanProjectResult()
    data class Failure(val error: KanbanError) : KanbanProjectResult()
}
