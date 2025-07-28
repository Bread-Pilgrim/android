package com.twolskone.bakeroad.core.common.android.base.util

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

object FileUtil {

    private const val TAG = "FileUtil"
    private const val MAX_SIZE_IMAGE = 20   // MB.

    @Throws(
        FileNotFoundException::class,
        SecurityException::class,
        IOException::class
    )
    suspend fun getImageFileFromUri(
        context: Context,
        uri: Uri,
        extension: String = "jpg"
    ): File = withContext(Dispatchers.IO) {
        val file = createTempImageFile(context = context, extension = extension)
        compressFileFromUri(context = context, file = file, uri = uri)
        file
    }

    @Throws(IOException::class)
    private fun createTempImageFile(
        context: Context,
        extension: String = "jpg"
    ): File {
        val currentTimeMillis = System.currentTimeMillis()
        val fileName = "temp_$currentTimeMillis"
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            /* prefix */ fileName,
            /* suffix */ ".$extension",
            /* directory */ directory
        )
    }

    @Throws(
        FileNotFoundException::class,
        SecurityException::class,
        IOException::class
    )
    private fun compressFileFromUri(
        context: Context,
        uri: Uri,
        file: File,
        compressFormat: CompressFormat = CompressFormat.JPEG,
        quality: Int = 50,
    ) {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val bitmap = BitmapFactory.decodeStream(inputStream) ?: run {
                val errorMessage = "Bitmap is null"
                Timber.tag(TAG).e("compressFileFromUri :: $errorMessage")
                throw IOException(errorMessage)
            }
            FileOutputStream(file).use { outputStream ->
                val compressResult = bitmap.compress(
                    /* compressFormat*/ compressFormat,
                    /* quality */ quality,
                    /* stream */ outputStream
                )
                Timber.tag(TAG).i("compressFileFromUri :: compress result >> $compressResult")
                if (!compressResult) {
                    throw IOException("Bitmap compression failed")
                }
            }
        } ?: run {
            val errorMessage = "Cannot open InputStream for uri: $uri"
            Timber.tag(TAG).e("compressFileFromUri :: $errorMessage")
            throw IOException(errorMessage)
        }
    }

    fun validateImageSizeFromUri(context: Context, uri: Uri): Boolean {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (sizeIndex != -1) {
                cursor.moveToFirst()
                return (bytesToMegaBytes(cursor.getLong(sizeIndex)) < MAX_SIZE_IMAGE)
            }
        }

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            return (bytesToMegaBytes(inputStream.available().toLong()) < MAX_SIZE_IMAGE)
        }

        return false
    }

    private fun bytesToMegaBytes(bytes: Long): Double = bytes.toDouble() / (1024 * 1024)
}