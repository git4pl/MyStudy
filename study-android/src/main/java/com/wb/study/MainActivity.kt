package com.wb.study

import android.database.Cursor
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.provider.MediaStore.Images
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.wb.study.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val mainBinding = setContentView(this, R.layout.activity_main) as ActivityMainBinding

        val book = Book("Android 高质量编程指北", "Alex PL", 5)
        mainBinding.setVariable(BR.book, book)
        //mainBinding.book = book

        //getVideoHeight(getLastImagePath())

        val test = "      "
        Log.d("TAG", "is test empty: " + TextUtils.isEmpty(test))
    }

    /**
     * Gets the last image id from the media store
     *
     * @return
     */
    private fun getLastImagePath(): String {
        val imageColumns = arrayOf(Images.Media._ID, Images.Media.DATA)
        val imageOrderBy = Images.Media._ID + " DESC"
        val imageCursor: Cursor = managedQuery(
            Images.Media.EXTERNAL_CONTENT_URI,
            imageColumns,
            null,
            null,
            imageOrderBy
        )
        return if (imageCursor.moveToFirst()) {
            val id: Int =
                imageCursor.getInt(imageCursor.getColumnIndex(Images.Media._ID))
            val fullPath: String =
                imageCursor.getString(imageCursor.getColumnIndex(Images.Media.DATA))
            Log.d(javaClass.name, "getLastImageId::id $id")
            Log.d(javaClass.name, "getLastImageId::path $fullPath")
            imageCursor.close()
            fullPath
        } else {
            ""
        }
    }

    private fun getVideoHeight(path: String) {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource("/storage/emulated/0/DCIM/Camera/VID_20200720_171425.mp4")
        val width =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        val height =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        val rotation =
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)
        val duration =
            java.lang.Long.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000
        Log.d(
            javaClass.name,
            "the video params are: width=$width, height=$height, \nduration=$duration, rotation=$rotation"
        )
    }
}