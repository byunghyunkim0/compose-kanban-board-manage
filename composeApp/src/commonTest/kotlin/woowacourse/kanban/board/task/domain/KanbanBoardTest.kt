package woowacourse.kanban.board.task.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanBoardTest {
    @Test
    fun `칸반 상태에 따라 카드를 분류한다`() {
        val card = listOf(
            createKanbanCard(status = KanbanStatus.IN_PROGRESS),
            createKanbanCard(status = KanbanStatus.TO_DO),
            createKanbanCard(status = KanbanStatus.TO_DO),
            createKanbanCard(status = KanbanStatus.DONE),
        )

        val board = KanbanBoard(title = "보드1", cards = card)

        assertThat(board.getCardByStatus(KanbanStatus.IN_PROGRESS).size).isEqualTo(1)
        assertThat(board.getCardByStatus(KanbanStatus.TO_DO).size).isEqualTo(2)
        assertThat(board.getCardByStatus(KanbanStatus.DONE).size).isEqualTo(1)
    }

    private fun createKanbanCard(id: Long = 0, status: KanbanStatus) = KanbanCard(
        id = id,
        boardId = 0,
        title = "제목",
        assigneeName = "담당자",
        status = status,
    )
}
