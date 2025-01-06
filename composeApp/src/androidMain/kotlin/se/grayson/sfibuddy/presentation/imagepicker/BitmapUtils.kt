package se.grayson.sfibuddy.presentation.imagepicker

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.InputStream

object BitmapUtils {
    private const val TAG = "BitmapUtils"

    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): android.graphics.Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            val s = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            return s
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(TAG, "getBitmapFromUri Exception: ${e.message}")
            Log.i(TAG, "getBitmapFromUri Exception: ${e.localizedMessage}")
            return null
        }
    }
}