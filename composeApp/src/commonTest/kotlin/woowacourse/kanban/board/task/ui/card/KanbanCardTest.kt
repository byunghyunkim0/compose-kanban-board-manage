package woowacourse.kanban.board.task.ui.card

import androidx.compose.ui.semantics.SemanticsActions.GetTextLayoutResult
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.TextLayoutResult
import kotlin.test.assertEquals
import org.junit.Test
import woowacourse.kanban.board.task.domain.KanbanCardForm

@OptIn(ExperimentalTestApi::class)
class KanbanCardTest {
    @Test
    fun `모든 필드가 있는 카드 테스트`() = runComposeUiTest {
        // given
        val kanbanCardForm = KanbanCardForm(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "다이노",
            tags = listOf(
                "컴포넌트",
                "성능",
            ),
            content = "세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.",
        )

        // when
        setContent {
            KanbanCardItem(
                kanbanCardForm = kanbanCardForm,
            )
        }

        // then
        onNodeWithText("LazyColumn 컴포넌트 구현").assertExists()
        onNodeWithText("다이노").assertExists()
        onNodeWithText("세로 스크롤 가능한 리스트 컴포넌트를 만들고 성능 최적화를 적용합니다.").assertExists()
        onNodeWithText("컴포넌트").assertExists()
        onNodeWithText("성능").assertExists()
    }

    @Test
    fun `content가 비어 있는 경우 UI 테스트`() = runComposeUiTest {
        // given
        val kanbanCardForm = KanbanCardForm(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "다이노",
            tags = listOf(
                "컴포넌트",
                "성능",
            ),
        )

        // when
        setContent {
            KanbanCardItem(
                kanbanCardForm = kanbanCardForm,
            )
        }

        // then
        onNodeWithTag("content").assertDoesNotExist()
    }

    @Test
    fun `content가 공백인 경우 UI 테스트`() = runComposeUiTest {
        val content = "       "
        // given
        val kanbanCardForm = KanbanCardForm(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "다이노",
            tags = listOf(
                "컴포넌트",
                "성능",
            ),
            content = content,
        )

        // when
        setContent {
            KanbanCardItem(
                kanbanCardForm = kanbanCardForm,
            )
        }

        // then
        onNodeWithText(content).assertDoesNotExist()
    }

    @Test
    fun `긴 담당자 말줄임표 발생 테스트`() = runComposeUiTest {
        val crewName = "너무 긴 담당자 이름너무 긴 담당자 이름너무 긴 담당자 이름"
        val kanbanCardForm = KanbanCardForm(
            title = "제목",
            crewName = crewName,
        )

        setContent {
            KanbanCardItem(
                kanbanCardForm = kanbanCardForm,
            )
        }

        val textLayoutResult = mutableListOf<TextLayoutResult>()
        onNodeWithText(crewName, useUnmergedTree = true).performSemanticsAction(GetTextLayoutResult) {
            it(textLayoutResult)
        }

        assertEquals(textLayoutResult.first().hasVisualOverflow, true)
    }

    @Test
    fun `프로필 이미지가 없다면 기본 이미지 출력 테스트`() = runComposeUiTest {
        val kanbanCardForm = KanbanCardForm(
            title = "LazyColumn 컴포넌트 구현",
            crewName = "다이노",
        )

        setContent {
            KanbanCardItem(
                kanbanCardForm = kanbanCardForm,
            )
        }

        onNodeWithContentDescription("기본 이미지").assertExists()
    }
}
