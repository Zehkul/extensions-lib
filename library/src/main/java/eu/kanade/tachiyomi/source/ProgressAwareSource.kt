package eu.kanade.tachiyomi.source

import eu.kanade.tachiyomi.source.model.ChapterProgress
import eu.kanade.tachiyomi.source.model.SManga

/**
 * Optional capability for sources that store reading progress on their own server
 * (Kavita, Komga, Suwayomi, …).
 *
 * Default methods are no-ops so ordinary sources are unaffected.
 * Host policy (when to push/pull, conflict rules, offline queue) lives in the app;
 * this interface is only the source-facing surface.
 *
 * @since extensions-lib ProgressAwareSource
 */
interface ProgressAwareSource {

    /**
     * Push one or more progress records.
     *
     * Must be idempotent. Later records for the same [ChapterProgress.chapterUrl]
     * supersede earlier ones. May be called with a batch; the source is free to
     * collapse them into a single network call.
     */
    suspend fun updateProgress(manga: SManga, progress: List<ChapterProgress>) {}

    /**
     * Pull progress for the given chapter URLs.
     *
     * Return only chapters the source knows about. Empty list means “I have nothing”.
     * The host never calls this for chapters it does not care about.
     */
    suspend fun getProgress(manga: SManga, chapterUrls: List<String>): List<ChapterProgress> =
        emptyList()

    /**
     * Optional bulk snapshot of all progress for [manga].
     *
     * If implemented, the host may call it once per series open and serve subsequent
     * [getProgress] requests from the returned map until the snapshot is invalidated
     * (series refresh, TTL, etc.).
     *
     * Return null if the source prefers per-chapter fetches.
     */
    suspend fun getProgressSnapshot(manga: SManga): Map<String, ChapterProgress>? = null
}
