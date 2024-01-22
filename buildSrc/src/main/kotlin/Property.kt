import org.gradle.api.Project
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidApp
import com.android.build.gradle.AppExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.findByType

object Property {

    val username: String by lazy { System.getProperty("user.name") }

    private lateinit var dateTime: LocalDateTime
    val buildDateTimeStr: String get() = dateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy | HH:mm:ss"))
    val buildTimeStr: String get() = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    lateinit var gitBranch: String private set
    lateinit var gitTicket: String private set
    lateinit var releaseNote: String private set

    fun init(project: Project) {
        initTime()
        initGitBranch(project)
        initGitTicket()
        initReleaseNote()
        project.log("Property.kt has been initialized")
    }

    private fun initTime() {
        dateTime = LocalDateTime.now()
    }

    private fun initGitBranch(project: Project) {
        gitBranch = ByteArrayOutputStream().also { outputStream ->
            project.exec {
                commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
                standardOutput = outputStream
            }
        }.toString().trim()
    }

    private fun initGitTicket() {
        var branch = gitBranch
        val a = branch.split("[_\\-\\/]".toRegex())
        if (a.size == 2) {
            branch = "${a[0]}_${a[1]}"
        } else if (a.size > 2 && branch.contains("/")) {
            branch = "${a[0]}_${a[1]}_${a[2]}"
        }
        gitTicket = branch
    }

    private fun getVersionNameSuffix(): String {
        val prefix = VersionLoader.versionName
        val l1 = dateTime
        val d = l1.dayOfYear.toString().padStart(3, '0')
        val h = l1.hour.toString().padStart(2, '0')
        return "$prefix-${l1.year.toString().substring(2, 4)}.${d}.${h}"
    }

    private fun initReleaseNote() {
        releaseNote = buildString {
            append("version: ${getVersionNameSuffix()}\n")
            append("author: ${System.getProperty("user.name")}\n")
            append("buildDate: ${buildDateTimeStr}\n")
            append("buildTime: ${buildTimeStr}\n")
            append("branch: ${gitBranch}\n")
            append("message:")
        }
    }
}