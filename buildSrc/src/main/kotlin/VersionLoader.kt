import org.gradle.api.Project
import org.gradle.api.logging.Logger
import kotlin.math.floor
import kotlin.math.round

@Suppress("MemberVisibilityCanBePrivate")
object VersionLoader {

    private var prefix = 0
    private var major = 0
    private var minor = 0
    private var release = 0
    private var revision = 0
    var versionCode: Int = 0
    var versionName: String = ""

    fun init(project: Project) {
        val properties = project.loadProperties("version.properties")
        prefix = (properties["PREFIX"] as String).toInt()
        major = (properties["MAJOR"] as String).toInt()
        minor = (properties["MINOR"] as String).toInt()
        release = (properties["RELEASE"] as String).toInt()
        revision = (properties["REVISION"] as String).toInt()

        versionCode = prefix * 10000000 + major * 100000 + minor * 1000 + release * 10 + revision
        versionName = "$major.$minor.$release"
        project.log("VersionLoader.kt has been initialized")
    }

    fun print(logger: Logger) {
        val format = "%02d"
        val printPrefix      = "Prefix       : $prefix"
        val printMajor       = "Major        : ${format.format(major)}"
        val printMinor       = "Minor        : ${format.format(minor)}"
        val printRelease     = "Release      : ${format.format(release)}"
        val printRevision    = "Revision     : $revision"
        val printVersionCode = "Version Code : $versionCode"
        val printVersionName = "Version Name : $versionName"
        val items = listOf(printPrefix, printMajor, printMinor, printRelease, printRevision, null, printVersionCode, printVersionName)
        val it = boxDrawing(" Version Info ", items, null)
        logger.error(it)
    }

    private fun boxDrawing(top: String?, items: List<String?>, bottom: String?): String? {
        if (top == null && bottom == null && items.isEmpty()) return null
        val maxLength = items.maxOf { it?.length ?: 0 }
        return buildString {
            if (!top.isNullOrBlank()) {
                (maxLength - top.length + 2).let {
                    appendLine("┏" + "━".repeat(floor(it / 2f).toInt()) + top + "━".repeat(round(it / 2f).toInt()) + "┓")
                }
            } else appendLine("┏" + "━".repeat(maxLength + 2) + "┓")
            items.forEach {
                if (it.isNullOrBlank()) {
                    appendLine("┣" + "━".repeat(maxLength + 2) + "┫")
                    return@forEach
                }
                appendLine("┃ $it ${" ".repeat(maxLength - it.length)}┃")
            }
            if (!bottom.isNullOrBlank()) {
                (maxLength - bottom.length + 2).let {
                    appendLine("┗" + "━".repeat(floor(it / 2f).toInt()) + bottom + "━".repeat(round(it / 2f).toInt()) + "┛")
                }
            } else appendLine("┗" + "━".repeat(maxLength + 2) + "┛")
        }
    }
}