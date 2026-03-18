package woowacourse.kanban.board.task.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanBoardTest {
    @Test
    fun `칸반 카드가 없으면 완료율은 0이다`() {
        val board = KanbanBoard(emptyList())

        assertThat(board.progress).isEqualTo(0)
    }

    @Test
    fun `칸반 카드의 완료율을 계산한다`() {
        val cards = listOf(
            createKanbanCard(status = KanbanStatus.TO_DO),
            createKanbanCard(status = KanbanStatus.IN_PROGRESS),
            createKanbanCard(status = KanbanStatus.DONE),
            createKanbanCard(status = KanbanStatus.DONE),
        )

        val board = KanbanBoard(cards = cards)

        assertThat(board.progress).isEqualTo(50)
    }

    @Test
    fun `모든 칸반 카드의 상태가 완료되면 완료율은 100이다`() {
        val cards = listOf(
            createKanbanCard(status = KanbanStatus.DONE),
            createKanbanCard(status = KanbanStatus.DONE),
            createKanbanCard(status = KanbanStatus.DONE),
        )

        val board = KanbanBoard(cards = cards)

        assertThat(board.progress).isEqualTo(100)
    }

    @Test
    fun `칸반 상태에 따라 카드를 분류한다`() {
        val card = listOf(
            createKanbanCard(status = KanbanStatus.IN_PROGRESS),
            createKanbanCard(status = KanbanStatus.TO_DO),
            createKanbanCard(status = KanbanStatus.TO_DO),
            createKanbanCard(status = KanbanStatus.DONE),
        )

        val board = KanbanBoard(cards = card)

        assertThat(board.getCardByStatus(KanbanStatus.IN_PROGRESS).size).isEqualTo(1)
        assertThat(board.getCardByStatus(KanbanStatus.TO_DO).size).isEqualTo(2)
        assertThat(board.getCardByStatus(KanbanStatus.DONE).size).isEqualTo(1)
    }

    @Test
    fun `칸반 카드 폼으로 카드를 추가한다`() {
        val board = KanbanBoard()
        val kanbanCardForm = KanbanCardForm(
            title = "제목",
            crewName = "담당자",
        )
        val kanbanCardForm1 = KanbanCardForm(
            title = "제목1",
            crewName = "담당자1",
        )

        val newBoard = board.addCard(
            kanbanCardForm = kanbanCardForm,
            status = KanbanStatus.TO_DO,
        )
        val newBoard1 = newBoard.addCard(
            kanbanCardForm = kanbanCardForm1,
            status = KanbanStatus.TO_DO,
        )

        assertThat(newBoard1.cards.size).isEqualTo(2)
        assertThat(newBoard1.cards.first().id).isEqualTo(1L)
        assertThat(newBoard1.cards.first().title).isEqualTo("제목")
        assertThat(newBoard1.cards.last().id).isEqualTo(2L)
        assertThat(newBoard1.cards.last().title).isEqualTo("제목1")
    }

    private fun createKanbanCard(id: Long = 0, status: KanbanStatus) = KanbanCard(
        id = id,
        title = "제목",
        assigneeName = "담당자",
        status = status,
    )
}
