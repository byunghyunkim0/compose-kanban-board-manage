package woowacourse.kanban.board.task.domain

enum class KanbanStatus {
    TO_DO,
    IN_PROGRESS,
    REVIEW,
    DONE,
    ;

    fun isTranslationStatus(toStatus: KanbanStatus): Boolean {
        return when (this) {
            TO_DO -> toStatus in listOf(TO_DO, IN_PROGRESS)
            IN_PROGRESS -> toStatus in listOf(TO_DO, IN_PROGRESS, REVIEW)
            REVIEW -> toStatus in listOf(IN_PROGRESS, REVIEW, DONE)
            DONE -> toStatus in listOf(TO_DO, DONE)
        }
    }

    fun isDeletable(): Boolean {
        return when (this) {
            TO_DO -> true
            IN_PROGRESS -> true
            REVIEW -> false
            DONE -> false
        }
    }

    fun isAssigneeRequired(): Boolean {
        return when (this) {
            TO_DO -> false
            IN_PROGRESS -> true
            REVIEW -> true
            DONE -> true
        }
    }
}
