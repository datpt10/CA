package vn.base.mvvm.utils.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object FilePath {
    val imageExts: ArrayList<String>
        get() {
            val imageTypes = arrayOf("png", "jpg", "jpeg", "bmp", "gif")

            return imageTypes.indices.mapTo(ArrayList()) { imageTypes[it] }
        }

    val videoExts: ArrayList<String>
        get() {
            val videoTypes = arrayOf("mpeg", "mp4", "gif", "wmv", "mov", "mpg", "3gp", "flv")
            return videoTypes.indices.mapTo(ArrayList()) { videoTypes[it] }
        }

    val docExts: ArrayList<String>
        get() {
            val docTypes = arrayOf("doc", "docx", "pdf", "txt")
            return docTypes.indices.mapTo(ArrayList()) { docTypes[it] }
        }


    fun pathFromUri(context: Context, uri: Uri): String? {
        Log.e("FileUtils", "authority= ${uri.authority}  scheme= ${uri.scheme}")
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
                val type = split[0]

                var contentUri: Uri? = null
                Log.e("FileUtils", "type: $type id: $docId")
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> return getDocument(context, uri)

                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(
                    context,
                    contentUri,
                    selection,
                    selectionArgs
                )
            } else
                return if (isGooglePhotosUri(uri)) getDocument(context, uri) else
                getDataColumn(
                    context,
                    uri,
                    null,
                    null
                )
            // DownloadsProvider
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            return if (isGooglePhotosUri(uri)) getDocument(context, uri) else
                getDataColumn(
                    context,
                    uri,
                    null,
                    null
                )

        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        // File
        // MediaStore (and general)
        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {

        var cursor: Cursor? = null
        val column = "_data"
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            MediaStore.MediaColumns.RELATIVE_PATH
//        } else {
//            MediaStore.MediaColumns.DATA
//        }
        val projection = arrayOf(column)

        try {
            cursor =
                context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    // returns whether the Uri authority is Google Photos or google drive.
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
                || "com.google.android.apps.photos.contentprovider" == uri.authority
                || "com.google.android.apps.docs.storage" == uri.authority
    }

    fun getDocument(context: Context, uri: Uri): String? {
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
                val file = File(folder.absolutePath, name ?: "file")
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

    fun getFileName(
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

    fun getFileNameFromPath(path: String): String {
        val index = path.lastIndexOf(File.separator)
        return if (index >= 0)
            path.substring(index).replace("/", "")
        else path
    }

    fun isDocument(path: String): Boolean {
        val index = path.lastIndexOf(".")
        val ext = if (index >= 0)
            path.substring(index).replace(".", "")
        else path
        return docExts.contains(ext)
    }

    fun isVideo(path: String): Boolean {
        val index = path.lastIndexOf(".")
        val ext = if (index >= 0)
            path.substring(index).replace(".", "")
        else path
        return videoExts.contains(ext)
    }

    fun isImage(path: String): Boolean {
        val index = path.lastIndexOf(".")
        val ext = if (index >= 0)
            path.substring(index).replace(".", "")
        else path
        return imageExts.contains(ext)
    }
}