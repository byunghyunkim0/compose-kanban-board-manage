package woowacourse.kanban.board.task.domain

import kotlin.test.Test
import org.assertj.core.api.Assertions.assertThat

class KanbanProjectTest {
    @Test
    fun `boardId에 따라 KanbanCard를 분류한다`() {
        val kanbanProject = KanbanProject("프로젝트")

        val newKanbanProject = kanbanProject.addCard(
            createKanbanCard(
                0,
                0,
                status = KanbanStatus.IN_PROGRESS,
            ),
        ).addCard(
            createKanbanCard(
                1,
                0,
                status = KanbanStatus.DONE,
            ),
        ).addCard(
            createKanbanCard(
                2,
                1,
                status = KanbanStatus.DONE,
            ),
        )

        val boardIdList = newKanbanProject.getKanbanCardByBoardId(0)
        val boardIdList1 = newKanbanProject.getKanbanCardByBoardId(1)

        assertThat(boardIdList.size).isEqualTo(2)
        assertThat(boardIdList1.size).isEqualTo(1)
    }

    @Test
    fun `KanbanCard를 추가한다`() {
        val kanbanCard = createKanbanCard(
            cardId = 0,
            boardId = 0,
        )

        val kanbanProject = KanbanProject(projectTitle = "프로젝트")

        val newProject = kanbanProject.addCard(kanbanCard)

        assertThat(newProject.kanbanCards.size).isEqualTo(1)
    }

    @Test
    fun `KanbanCard의 Status를 수정한다`() {
        val kanbanProject = KanbanProject(
            projectTitle = "프로젝트",
        )

        val newKanbanProject = kanbanProject.addCard(
            createKanbanCard(
                0,
                0,
                status = KanbanStatus.IN_PROGRESS,
            ),
        ).addCard(
            createKanbanCard(
                1,
                0,
                status = KanbanStatus.DONE,
            ),
        ).addCard(
            createKanbanCard(
                2,
                0,
                status = KanbanStatus.DONE,
            ),
        )

        val updateKanbanProject = newKanbanProject.updateCardStatus(
            id = 1,
            status = KanbanStatus.TO_DO,
        )

        assertThat(updateKanbanProject.getKanbanCard(1)?.status).isEqualTo(KanbanStatus.TO_DO)
    }

    private fun createKanbanCard(cardId: Long, boardId: Int, status: KanbanStatus = KanbanStatus.TO_DO) = KanbanCard(
        id = cardId,
        boardId = boardId,
        title = "제목",
        assigneeName = "담당자",
        status = status,
    )
}
