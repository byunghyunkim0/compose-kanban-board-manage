package woowacourse.kanban.board.task.domain

data class KanbanProject(val projectTitle: String, val kanbanCards: List<KanbanCard> = emptyList()) {
    fun updateCardStatus(id: Int, status: KanbanStatus): KanbanProject {
        return copy()
    }

    fun addCard(kanbanCard: KanbanCard): KanbanProject {
        return copy()
    }

    fun getKanbanCard(id: Int): KanbanCard {
        return kanbanCards.first()
    }

    fun getKanbanCardByBoardId(boardId: Int): List<KanbanCard> {
        return emptyList()
    }
}
