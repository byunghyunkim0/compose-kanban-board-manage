package woowacourse.kanban.board.task.domain

data class KanbanBoard(val boardId: Int, val title: String, val cards: List<KanbanCard> = emptyList()) {
    val totalCount: Int = cards.size
    val doneCount: Int = cards.count { it.status == KanbanStatus.DONE }
    val progress: Int = if (totalCount == 0) 0 else (doneCount * 100) / totalCount

    fun getCardByStatus(status: KanbanStatus) = cards.filter { it.status == status }

    fun getCard(cardId: String): KanbanCard? = cards.find { it.id == cardId }

    fun addCard(card: KanbanCard): KanbanBoard = copy(cards = cards + card)

    fun updateCardStatus(cardId: String, status: KanbanStatus): KanbanBoard? {
        val targetCard = getCard(cardId) ?: return null
        val newCard = targetCard.updateStatus(status)

        val newCards = cards.map {
            if (it.id == cardId) newCard
            else it
        }
        return copy(cards = newCards)
    }
}
