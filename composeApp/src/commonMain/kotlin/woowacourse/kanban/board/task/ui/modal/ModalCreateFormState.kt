package woowacourse.kanban.board.task.ui.modal

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.task.domain.KanbanCard
import woowacourse.kanban.board.task.domain.KanbanStatus
import woowacourse.kanban.board.task.domain.TaskErrorType
import woowacourse.kanban.board.task.domain.TaskValidator

class ModalCreateFormState {
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var tag by mutableStateOf("")
    var status by mutableIntStateOf(0)
    var assignee by mutableIntStateOf(0)

    var validTitle by mutableStateOf(TaskErrorType.DEFAULT)

    val isValidTitle by derivedStateOf {
        validTitle == TaskErrorType.DEFAULT
    }

    var validTag by mutableStateOf(TaskErrorType.TAG_DEFAULT)

    val isValidTag by derivedStateOf {
        validTag == TaskErrorType.TAG_DEFAULT
    }

    fun resetTitleError() {
        validTitle = TaskErrorType.DEFAULT
    }

    fun resetTagError() {
        validTag = TaskErrorType.TAG_DEFAULT
    }

    fun validate(): Boolean {
        validTitle = TaskValidator.validateTitle(title)
        validTag = TaskValidator.validateTags(tag)
        return validTitle == TaskErrorType.DEFAULT && validTag == TaskErrorType.TAG_DEFAULT
    }

    fun toCard(assignees: List<String>): KanbanCard {
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
