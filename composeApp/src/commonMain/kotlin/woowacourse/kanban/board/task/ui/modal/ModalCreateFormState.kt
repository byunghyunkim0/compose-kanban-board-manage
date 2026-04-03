package woowacourse.kanban.board.task.ui.modal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanCard.Companion.validateTags
import woowacourse.kanban.board.task.domain.KanbanCard.Companion.validateTitle
import woowacourse.kanban.board.task.domain.KanbanCardError
import woowacourse.kanban.board.task.domain.KanbanStatus

@Composable
fun RememberModalCreateFormState(assignees: List<String>): ModalCreateFormState {
    return remember { ModalCreateFormState(assignees = assignees) }
}

class ModalCreateFormState(val assignees: List<String>) {
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var tag by mutableStateOf("")
    var status by mutableIntStateOf(0)
    var assignee by mutableIntStateOf(0)

    var validTitle: KanbanCardError? by mutableStateOf(null)

    val isValidTitle by derivedStateOf {
        validTitle == null
    }

    var validTag: KanbanCardError? by mutableStateOf(null)

    val isValidTag by derivedStateOf {
        validTag == null
    }

    fun resetTitleError() {
        validTitle = null
    }

    fun resetTagError() {
        validTag = null
    }

    fun validate(): Boolean {
        validTitle = validateTitle(title)

        val tags = if (tag.isEmpty()) emptyList() else tag.split(",").map { it.trim() }
        validTag = validateTags(tags)
        return validTitle == null && validTag == null
    }

    fun toCard(): KanbanCard {
        val tags = if (tag.isEmpty()) emptyList() else tag.split(",").map { it.trim() }
        return KanbanCard(
            title = title,
            content = content,
            tags = tags,
            assigneeName = assignees[assignee],
            status = KanbanStatus.entries[status],
        )
    }
}
