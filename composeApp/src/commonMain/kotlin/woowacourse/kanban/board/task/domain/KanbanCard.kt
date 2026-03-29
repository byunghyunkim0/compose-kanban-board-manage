package woowacourse.kanban.board.task.domain

import java.util.UUID

data class KanbanCard(
    val id: String = UUID.randomUUID().toString(),
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
