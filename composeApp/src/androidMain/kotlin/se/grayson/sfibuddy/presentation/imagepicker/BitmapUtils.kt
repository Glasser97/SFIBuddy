package se.grayson.sfibuddy.presentation.imagepicker



import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.InputStream

object BitmapUtils {
    private const val TAG = "BitmapUtils"

    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver, needDownsizeSample: Boolean = false): android.graphics.Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            var options: BitmapFactory.Options? = null
            if (needDownsizeSample) {
                options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                options.inSampleSize = 8
            }
            val s = BitmapFactory.decodeStream(inputStream, null, options)
            return s
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(TAG, "getBitmapFromUri Exception: ${e.message}")
            Log.i(TAG, "getBitmapFromUri Exception: ${e.localizedMessage}")
            return null
        } finally {
            inputStream?.close()
        }
    }
}