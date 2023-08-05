package vn.base.mvvm.choosePhotoHelper.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @author aminography
 */

/**
 * Finds uri for the file using [FileProvider] to avoid [android.os.FileUriExposedException] for APIs >= 24.
 *
 * @param context a context
 *
 * @return uri of the input file.
 */
fun File.grantedUri(context: Context): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.provider",
            this
        )
    } else {
        Uri.fromFile(this)
    }
}

/**
 * Finds real path of the file which is addressed by the uri.
 *
 * @param context a context
 * @param uri content uri to find real path
 *
 * @return real path of the file addressing by the uri.
 */
@Suppress("DEPRECATION")
fun pathFromUri(context: Context, uri: Uri): String? {
    // DocumentProvider
    if (DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
            //handle non-primary volumes
        } else if (isDownloadsDocument(uri)) {
            getFileName(context, uri)?.let { fileName ->
                return Environment.getExternalStorageDirectory()
                    .toString() + "/Download/" + fileName
            }

            var documentId = DocumentsContract.getDocumentId(uri)
            if (documentId.startsWith("raw:")) {
                documentId = documentId.replace("raw:", "")
                if (File(documentId).exists()) {
                    return documentId
                }
            }

            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                documentId.toLong()
            )
            return getDataColumn(
                context,
                contentUri,
                null,
                null
            )
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val contentUri: Uri? = when (val type = split[0]) {
                "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                else -> {
                    Log.e("FileUtils", "type: $type id: $docId")
                    return getDocument(context, uri)
                }

            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            return getDataColumn(
                context,
                contentUri,
                selection,
                selectionArgs
            )
        }// MediaProvider
        // DownloadsProvider
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        // Return the remote address
        return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
            context,
            uri,
            null,
            null
        )
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }// File
    // MediaStore (and general)
    return null
}

// returns whether the Uri authority is ExternalStorageProvider.
private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

// returns whether the Uri authority is DownloadsProvider.
private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

// returns whether the Uri authority is MediaProvider.
private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

// returns whether the Uri authority is Google Photos.
private fun isGooglePhotosUri(uri: Uri): Boolean {
    return "com.google.android.apps.photos.content" == uri.authority
}

private fun getDataColumn(
    context: Context,
    uri: Uri?,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(index)
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    } finally {
        cursor?.close()
    }
    return null
}


private fun getDocument(context: Context, uri: Uri): String? {
    var input: FileInputStream?
    var output: FileOutputStream?
    try {

        context.contentResolver.openFileDescriptor(uri, "r")?.let { pfd ->
            val fd = pfd.fileDescriptor
            input = FileInputStream(fd)

            val folder = File(context.cacheDir, "documents")

            if (!folder.exists()) {
                folder.mkdirs()
            }

            val name = getFileName(context, uri)
            val file = File(folder.absolutePath, name ?: "document.pdf")
            val filePath = file.absolutePath
            output = FileOutputStream(filePath)

//            val name  =  getFileName(context, uri)
//            val filePath= File(context.cacheDir, "$name").absolutePath
//            output = FileOutputStream(filePath)

            var read: Int
            val bytes = ByteArray(4096)
            while (input!!.read(bytes).also { read = it } != -1) {
                output!!.write(bytes, 0, read)
            }
            val sharedFile = File(filePath)
            val path = sharedFile.path



            return path
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return null
}

private fun getFileName(
    context: Context,
    uri: Uri
): String? {
    var cursor: Cursor? = null
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

    try {
        cursor = context.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            return cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }
    return null
}
