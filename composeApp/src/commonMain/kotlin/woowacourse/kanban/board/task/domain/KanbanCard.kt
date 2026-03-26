package woowacourse.kanban.board.task.domain

data class KanbanCard(
    val id: Long,
    val boardId: Int,
    val title: String,
    val assigneeName: String,
    val status: KanbanStatus,
    val content: String = "",
    val tags: List<String> = emptyList(),
) {
    fun updateStatus(status: KanbanStatus): KanbanCard {
        return copy(status = status)
    }
}
