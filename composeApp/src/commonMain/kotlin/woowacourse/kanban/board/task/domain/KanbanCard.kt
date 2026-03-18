package woowacourse.kanban.board.task.domain

data class KanbanCard(
    val id: Long,
    val title: String,
    val assigneeName: String,
    val status: KanbanStatus,
    val content: String = "",
    val tags: List<String> = emptyList(),
)
