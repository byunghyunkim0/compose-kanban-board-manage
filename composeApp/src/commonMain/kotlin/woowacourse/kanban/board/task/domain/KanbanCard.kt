package woowacourse.kanban.board.task.domain

import java.util.UUID

data class KanbanCard(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val assigneeName: String,
    val status: KanbanStatus,
    val content: String = "",
    val tags: List<String> = emptyList(),
) {
    init {
        val titleError = validateTitle(title)
        require(titleError == null) { "칸반 카드의 제목이 올바르지 않습니다. - 제목: $title" }
        val tagError = validateTags(tags)
        require(tagError == null) { "칸반 카드의 태그 형식이 올바르지 않습니다. - 에러 타입: $tagError, tags: $tags" }
    }

    fun updateStatus(status: KanbanStatus): KanbanCard {
        return copy(status = status)
    }

    companion object {
        const val MAX_TAG_COUNT = 5
        const val MAX_TAG_LENGTH = 5

        fun validateTitle(title: String): KanbanCardError? {
            if (title.isBlank()) return KanbanCardError.TITLE_FORMAT
            return null
        }

        fun validateTags(tags: List<String>): KanbanCardError? {
            if (tags.any { it.isBlank() }) return KanbanCardError.TAG_FORMAT
            if (tags.size > MAX_TAG_COUNT || tags.any { it.length > MAX_TAG_LENGTH }) return KanbanCardError.TAG_SIZE
            return null
        }
    }
}

enum class KanbanCardError {
    TAG_FORMAT,
    TAG_SIZE,
    TITLE_FORMAT,
}
