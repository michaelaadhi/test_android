import org.gradle.api.Project
import org.json.JSONObject

object KromBuildConfigLoader {

    var staging: String? = null
        private set

    var production: String? = null
        private set


    fun init(project: Project) {
        val file = project.file("${project.rootDir}/buildSrc/raw/result.json").takeIf { it.exists() }
        val result = file?.readText() ?: return
        val json = JSONObject(result)
        staging = try {
            json.getString("ANDROID_STAGING")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        production = try {
            json.getString("ANDROID_PRODUCTION")
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        project.log("KromBuildConfigLoader.kt has been initialized")
    }
}