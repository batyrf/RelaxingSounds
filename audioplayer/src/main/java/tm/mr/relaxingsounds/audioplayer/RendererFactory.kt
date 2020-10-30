package tm.mr.relaxingsounds.audioplayer

import android.content.Context
import android.os.Handler
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.Renderer
import com.google.android.exoplayer2.audio.AudioProcessor
import com.google.android.exoplayer2.audio.AudioRendererEventListener
import com.google.android.exoplayer2.audio.TeeAudioProcessor
import com.google.android.exoplayer2.drm.DrmSessionManager
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector
import java.nio.ByteBuffer
import java.util.ArrayList

class RendererFactory(
    context: Context,
    val onHandleBuffer: ((buffer: ByteBuffer?) -> Unit)?
) : DefaultRenderersFactory(context) {

    override fun buildAudioRenderers(
        context: Context?,
        extensionRendererMode: Int,
        mediaCodecSelector: MediaCodecSelector?,
        drmSessionManager: DrmSessionManager<FrameworkMediaCrypto>?,
        playClearSamplesWithoutKeys: Boolean,
        audioProcessors: Array<out AudioProcessor>?,
        eventHandler: Handler?,
        eventListener: AudioRendererEventListener?,
        out: ArrayList<Renderer>?
    ) {
        val mAudioProcessor = onHandleBuffer?.let {
            Array<AudioProcessor>(1) {
                TeeAudioProcessor(object : TeeAudioProcessor.AudioBufferSink {
                    override fun flush(sampleRateHz: Int, channelCount: Int, encoding: Int) {
                    }

                    override fun handleBuffer(buffer: ByteBuffer?) {
                        onHandleBuffer.invoke(buffer)
                    }
                })
            }
        } ?: audioProcessors
        super.buildAudioRenderers(context,
            extensionRendererMode,
            mediaCodecSelector,
            drmSessionManager,
            playClearSamplesWithoutKeys,
            mAudioProcessor,
            eventHandler,
            eventListener,
            out)
    }


}