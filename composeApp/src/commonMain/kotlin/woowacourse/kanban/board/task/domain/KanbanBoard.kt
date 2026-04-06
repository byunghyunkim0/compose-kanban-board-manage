package woowacourse.kanban.board.task.domain

data class KanbanBoard(val boardId: Int, val title: String, val cards: List<KanbanCard> = emptyList()) {
    val totalCount: Int = cards.size
    val doneCount: Int = cards.count { it.status == KanbanStatus.DONE }
    val progress: Int = if (totalCount == 0) 0 else (doneCount * 100) / totalCount

    fun getCardByStatus(status: KanbanStatus) = cards.filter { it.status == status }

    fun getCard(cardId: String): KanbanCard? = cards.find { it.id == cardId }

    fun addCard(card: KanbanCard): KanbanBoardResult {
        return KanbanBoardResult.Success(copy(cards = cards + card))
    }

    fun updateCardStatus(cardId: String, status: KanbanStatus): KanbanBoardResult {
        val targetCard = getCard(cardId) ?: return KanbanBoardResult.Failure(KanbanError.KANBAN_NOT_FOUND)
        return when (val cardResult = targetCard.updateStatus(status)) {
            is KanbanCardResult.Failure -> KanbanBoardResult.Failure(cardResult.error)
            is KanbanCardResult.Success -> {
                val newCards = cards.map { if (it.id == cardId) cardResult.card else it }
                KanbanBoardResult.Success(copy(cards = newCards))
            }
        }
    }

    fun updateCard(cardId: String, updatedCard: KanbanCard): KanbanBoardResult {
        val targetCard = getCard(cardId) ?: return KanbanBoardResult.Failure(KanbanError.KANBAN_NOT_FOUND)
        return when (
            val cardResult = targetCard.updateCard(
                title = updatedCard.title,
                assigneeName = updatedCard.assigneeName,
                status = updatedCard.status,
                content = updatedCard.content,
                tags = updatedCard.tags,
            )
        ) {
            is KanbanCardResult.Failure -> KanbanBoardResult.Failure(cardResult.error)
            is KanbanCardResult.Success -> {
                val newCards = cards.map { if (it.id == cardId) cardResult.card else it }
                KanbanBoardResult.Success(copy(cards = newCards))
            }
        }
    }

    fun deleteCard(cardId: String): KanbanBoardResult {
        val targetCard = getCard(cardId) ?: return KanbanBoardResult.Failure(KanbanError.KANBAN_NOT_FOUND)
        if (!targetCard.status.isDeletable) return KanbanBoardResult.Failure(KanbanError.DELETION_NOT_ALLOWED)
        val newCards = cards.filter { it.id != targetCard.id }
        return KanbanBoardResult.Success(copy(cards = newCards))
    }
}
