import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.Dependency
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.util.Base64
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun Dependency.asString() = "${group}:${name}:${version}"

fun KaptExtension.configureObjectBox(packageName: String) = arguments {
    arg("objectbox.debug", true)
    arg("objectbox.myObjectBoxPackage", packageName)
}

fun KotlinJvmOptions.default() = this.also {
    jvmTarget = JavaVersion.current().toString()
}

fun ByteArray.encodeBase64(): String = Base64.getEncoder().encodeToString(this)

fun String.decodeBase64(): ByteArray = Base64.getDecoder().decode(this)

fun String.encodeBase64(): String = toByteArray(Charsets.UTF_8).encodeBase64()

fun String.gzipCompress(): ByteArray {
    val bos = ByteArrayOutputStream()
    GZIPOutputStream(bos)
        .bufferedWriter(Charsets.UTF_8)
        .use { it.write(this) }
    return bos.toByteArray()
}

fun ByteArray.gzipDecompress(): String {
    val bis = ByteArrayInputStream(this)
    return GZIPInputStream(bis)
        .bufferedReader(Charsets.UTF_8)
        .use { it.readText() }
}

fun File.readText() = bufferedReader(Charsets.UTF_8).use { it.readText() }

fun InputStream.gzipCompress(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    val gzip = GZIPOutputStream(outputStream)
    val buffer = ByteArray(1024)
    var r = read(buffer, 0, 1024)
    while (r > 0) {
        gzip.write(buffer, 0, r)
        r = read(buffer, 0, 1024)
    }
    close()
    gzip.close()

    val result = outputStream.toByteArray()
    outputStream.close()
    return result
}