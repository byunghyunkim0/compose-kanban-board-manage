package woowacourse.kanban.board.task.ui.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.status_Done
import kanbanboard.composeapp.generated.resources.status_In_Progress
import kanbanboard.composeapp.generated.resources.status_to_do
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanCardForm
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.ui.card.KanbanCardItem
import woowacourse.kanban.board.theme.DoneColumnBorder
import woowacourse.kanban.board.theme.DoneColumnContentBackground
import woowacourse.kanban.board.theme.DoneColumnHeaderBackground
import woowacourse.kanban.board.theme.InProgressColumnBorder
import woowacourse.kanban.board.theme.InProgressColumnContentBackground
import woowacourse.kanban.board.theme.InProgressColumnHeaderBackground
import woowacourse.kanban.board.theme.TodoColumnBorder
import woowacourse.kanban.board.theme.TodoColumnContentBackground
import woowacourse.kanban.board.theme.TodoColumnHeaderBackground

@Composable
fun KanbanBody(
    modifier: Modifier = Modifier,
    todoCards: List<KanbanCard> = emptyList(),
    inProgressCards: List<KanbanCard> = emptyList(),
    doneCards: List<KanbanCard> = emptyList(),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        KanbanColumn(
            status = KanbanStatus.TO_DO,
            cards = todoCards,
        )
        KanbanColumn(
            status = KanbanStatus.IN_PROGRESS,
            cards = inProgressCards,
        )
        KanbanColumn(
            KanbanStatus.DONE,
            cards = doneCards,
        )
    }
}

data class ColumnColors(val headerColor: Color, val backgroundColor: Color, val borderColor: Color)

@Composable
private fun KanbanColumn(status: KanbanStatus, cards: List<KanbanCard>, modifier: Modifier = Modifier) {
    val (title, color) = when (status) {
        KanbanStatus.TO_DO -> stringResource(Res.string.status_to_do) to ColumnColors(
            headerColor = TodoColumnHeaderBackground,
            backgroundColor = TodoColumnContentBackground,
            borderColor = TodoColumnBorder,
        )

        KanbanStatus.IN_PROGRESS -> stringResource(Res.string.status_In_Progress) to ColumnColors(
            headerColor = InProgressColumnHeaderBackground,
            backgroundColor = InProgressColumnContentBackground,
            borderColor = InProgressColumnBorder,
        )

        KanbanStatus.DONE -> stringResource(Res.string.status_Done) to ColumnColors(
            headerColor = DoneColumnHeaderBackground,
            backgroundColor = DoneColumnContentBackground,
            borderColor = DoneColumnBorder,
        )
    }

    Column(
        modifier = modifier.fillMaxHeight().width(320.dp).clip(RoundedCornerShape(10.dp)).border(
            width = 1.dp,
            color = color.borderColor,
            shape = RoundedCornerShape(10.dp),
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = color.headerColor).padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(
                        horizontal = 10.dp,
                        vertical = 2.dp,
                    ),
            ) {
                Text(
                    text = "${cards.size}",
                    fontSize = 14.sp,
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(color = color.backgroundColor)
                .padding(
                    start = 17.dp,
                    end = 17.dp,
                    top = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                key = { it.id },
                items = cards,
            ) {
                KanbanCardItem(
                    KanbanCardForm(
                        title = it.title,
                        crewName = it.assigneeName,
                        tags = it.tags,
                        content = it.content,
                    ),
                )
            }
        }
    }
}

@Preview(
    widthDp = 1300,
    heightDp = 800,
)
@Composable
private fun KanbanBodyPreview() {
    KanbanBody(
        todoCards = listOf(
            createTempCard(
                id = 1,
                boardId = 0,
                status = KanbanStatus.TO_DO,
            ),
            createTempCard(
                id = 2,
                boardId = 0,
                status = KanbanStatus.TO_DO,
            ),
        ),
        inProgressCards = listOf(
            createTempCard(
                id = 3,
                boardId = 0,
                status = KanbanStatus.IN_PROGRESS,
            ),
        ),
    )
}

private fun createTempCard(id: Long, boardId: Int, status: KanbanStatus) = KanbanCard(
    id = id,
    boardId = boardId,
    title = "제목",
    assigneeName = "담당자",
    tags = listOf("태그"),
    status = status,
)
