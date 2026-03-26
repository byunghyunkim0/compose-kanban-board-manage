package woowacourse.kanban.board.task.domain

data class KanbanBoard(val cards: List<KanbanCard> = emptyList(), val cardId: Long = 0) {
    val totalCount: Int get() = cards.size
    val doneCount: Int get() = cards.count { it.status == KanbanStatus.DONE }

    fun getCardByStatus(status: KanbanStatus) = cards.filter { it.status == status }

    fun addCard(kanbanCardForm: KanbanCardForm, status: KanbanStatus): KanbanBoard {
        val nextId = cardId + 1
        val card = KanbanCard(
            id = nextId,
            title = kanbanCardForm.title,
            content = kanbanCardForm.content,
            tags = kanbanCardForm.tags,
            status = status,
            assigneeName = kanbanCardForm.crewName,
        )

        return copy(cards = cards + card, cardId = nextId)
    }
}
