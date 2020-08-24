package tm.mr.relaxingsounds.audioplayer

import android.net.Uri
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import java.lang.Exception

val Uri.isHls: Boolean
    get() {
        return try {
            val str = this.toString()
            val extension = str.substring(str.lastIndexOf("."))
            extension.equals(".m3u8", false)
        } catch (e: Exception) {
            false
        }
    }

fun Uri.toMediaSource(dataSourceFactory: DataSource.Factory): MediaSource {
    return if (this.isHls) {
        this.toHlsMediaSource(dataSourceFactory)
    } else {
        this.toExtractorMediaSource(dataSourceFactory)
    }
}

fun Uri.toHlsMediaSource(dataSourceFactory: DataSource.Factory): HlsMediaSource {
    return HlsMediaSource.Factory(dataSourceFactory)
        .createMediaSource(this)
}


fun Uri.toExtractorMediaSource(dataSourceFactory: DataSource.Factory): ExtractorMediaSource {
    return ExtractorMediaSource
        .Factory(dataSourceFactory)
        .setExtractorsFactory(DefaultExtractorsFactory())
        .createMediaSource(this)
}


