package woowacourse.kanban.board.task.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class KanbanCardTest {

    @Test
    fun `KanbanCard의 Status가 변경된다`() {
        val kanbanCard = KanbanCard(
            id = 0,
            title = "칸반제목 1",
            assigneeName = "담당자 1",
            status = KanbanStatus.TO_DO,
        )
        kanbanCard.updateStatus(status = KanbanStatus.IN_PROGRESS)
        assertThat(kanbanCard.status).isEqualTo(KanbanStatus.IN_PROGRESS)
        assertThat(kanbanCard.status).isNotEqualTo(KanbanStatus.TO_DO)
        assertThat(kanbanCard.status).isNotEqualTo(KanbanStatus.DONE)
    }
}
