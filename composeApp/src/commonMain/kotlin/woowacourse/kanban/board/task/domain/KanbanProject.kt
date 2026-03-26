package woowacourse.kanban.board.task.domain

data class KanbanProject(val projectTitle: String, val kanbanCards: List<KanbanCard> = emptyList()) {
    fun updateCardStatus(id: Long, status: KanbanStatus): KanbanProject {
        val newCard = getKanbanCard(id).updateStatus(status)
        val updatedCards = kanbanCards.map { card ->
            if (card.id == id) newCard else card
        }
        return copy(kanbanCards = updatedCards.toList())
    }

    fun addCard(kanbanCard: KanbanCard) = copy(kanbanCards = kanbanCards + kanbanCard)

    fun addCard(boardId: Int, kanbanCardForm: KanbanCardForm, status: KanbanStatus): KanbanProject {
        val newId = (kanbanCards.maxOfOrNull { it.id } ?: 0) + 1

        val newCard = KanbanCard(
            id = newId,
            boardId = boardId,
            title = kanbanCardForm.title,
            content = kanbanCardForm.content,
            assigneeName = kanbanCardForm.crewName,
            status = status,
        )

        return copy(kanbanCards = kanbanCards + newCard)
    }

    fun getKanbanCard(id: Long): KanbanCard {
        val findKanbanCard = kanbanCards.find { it.id == id } ?: throw IllegalArgumentException("id가 ${id}인 카드를 찾지 못했습니다.")
        return findKanbanCard
    }

    fun getKanbanCardByBoardId(boardId: Int) = kanbanCards.filter { it.boardId == boardId }
}
