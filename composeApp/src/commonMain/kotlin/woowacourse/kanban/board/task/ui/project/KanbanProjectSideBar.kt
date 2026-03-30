package woowacourse.kanban.board.task.ui.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kanbanboard.composeapp.generated.resources.Res
import kanbanboard.composeapp.generated.resources.project_title
import org.jetbrains.compose.resources.stringResource
import woowacourse.kanban.board.theme.BoardSelectBackground
import woowacourse.kanban.board.theme.BoardSelectText
import woowacourse.kanban.board.theme.BorderButtonDefault

@Composable
fun KanbanProjectSideBar(title: String, boardTitle: List<String>, selected: Int, onClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(255.dp)
            .border(
                width = 1.dp,
                color = BorderButtonDefault,
            ),
    ) {
        // 헤더
        SideBarHeader(
            modifier = Modifier.fillMaxWidth(),
            title = title,
        )
        // 하단부
        SideBarItem(
            modifier = Modifier.fillMaxWidth(),
            kanbanBoardTitles = boardTitle,
            selected = selected,
            onClick = onClick,
        )
    }
}

@Composable
private fun SideBarHeader(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = BorderButtonDefault,
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = stringResource(Res.string.project_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun SideBarItem(selected: Int, kanbanBoardTitles: List<String>, onClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        kanbanBoardTitles.forEachIndexed { index, buttonText ->
            SideBarButton(
                modifier = Modifier.fillMaxWidth(),
                isSelected = selected == index,
                buttonText = buttonText,
                onClick = { onClick(index) },
            )
        }
    }
}

@Composable
private fun SideBarButton(isSelected: Boolean, buttonText: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val containerColor = if (isSelected) BoardSelectBackground else Color.Unspecified
    val contentColor = if (isSelected) BoardSelectText else Color.Black
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .background(color = containerColor)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
    ) {
        Text(
            text = buttonText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = contentColor,
        )
    }
}

@Preview
@Composable
private fun KanbanProjectSideBarPreview() {
    var selected by remember { mutableIntStateOf(0) }

    KanbanProjectSideBar(
        title = "4주차 미션 보드",
        boardTitle = listOf(
            "Compose1",
            "Compose2",
            "Compose3너무너무긴제목입니다.",
        ),
        selected = selected,
        onClick = { index ->
            selected = index
        },
    )
}
