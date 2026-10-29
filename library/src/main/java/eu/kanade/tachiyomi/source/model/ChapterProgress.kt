package eu.kanade.tachiyomi.source.model

/**
 * Page-level reading progress for one chapter, owned by the source.
 *
 * [pageIndex] is 0-based and uses the same coordinate as [Page.index] from
 * getPageList. When [completed] is true, pageIndex is meaningless and ignored.
 *
 * pageIndex 0 with completed false means reset to unread. Hosts send that only
 * when local state changes into unread (explicit mark-unread, or progress
 * rewound to the start), never for a chapter the user has not touched. Sources
 * that cannot distinguish never-read from reset to unread should return
 * (0, false) for both; hosts decide how to merge it with local state.
 *
 * New fields require a new secondary constructor (keeping this constructor) or
 * a library major version so already-compiled extensions keep linking.
 */
class ChapterProgress(
    val chapterUrl: String,
    val pageIndex: Int,
    val completed: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChapterProgress) return false
        return chapterUrl == other.chapterUrl &&
            pageIndex == other.pageIndex &&
            completed == other.completed
    }

    override fun hashCode(): Int {
        var result = chapterUrl.hashCode()
        result = 31 * result + pageIndex
        result = 31 * result + completed.hashCode()
        return result
    }

    // chapterUrl omitted: may contain tokens in query strings on some sources.
    override fun toString(): String =
        "ChapterProgress(pageIndex=$pageIndex, completed=$completed)"
}
