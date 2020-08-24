package tm.mr.relaxingsounds.audioplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class AudioPlayer(context: Context) {

    private var exoPlayer: SimpleExoPlayer
    private val userAgent = Util.getUserAgent(context, "CompactPlayerView")
    private val dataSourceFactory = DefaultDataSourceFactory(context, userAgent)
    private var mediaSource: MediaSource? = null
    init {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(context)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            context, renderersFactory, trackSelector, loadControl
        )
    }

    fun play(src: String) {
        if (mediaSource == null) {
            mediaSource = Uri.parse(src).toMediaSource(dataSourceFactory)
            exoPlayer.prepare(mediaSource)
        }
        exoPlayer.playWhenReady = true
    }

    fun pause() {
        exoPlayer.playWhenReady = false
    }

    fun setVolume(volume: Int) {
        exoPlayer.volume = volume.toFloat() / 10f
    }


}