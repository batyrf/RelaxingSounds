package tm.mr.relaxingsounds.ui.favorite.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*
import tm.mr.relaxingsounds.R
import tm.mr.relaxingsounds.audioplayer.AudioPlayer
import tm.mr.relaxingsounds.data.extension.ignoreNull
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.toast
import tm.mr.relaxingsounds.ui.favorite.viewmodel.FavoriteViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by viewModels()
    private val playerMap: MutableMap<String, AudioPlayer> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteRV.setOnLikeClickListener {
            viewModel.updateSound(it)
        }

        favoriteRV.setOnPlayPauseClickListener { sound, shouldPlay ->
            playerMap.getOrPut(sound.id) { AudioPlayer(view.context) }.apply {
                if (shouldPlay)
                    play(sound.url.ignoreNull())
                else
                    pause()
            }
        }

        favoriteRV.setOnVolumeChangeListener { sound, volume ->
            playerMap.getOrPut(sound.id) { AudioPlayer(view.context) }.apply {
                setVolume(volume)
            }
        }

        viewModel.sounds.observe(this as LifecycleOwner, Observer {
            when (it) {
                is Resource.success -> (favoriteRV.adapter as? FavoriteAdapter)?.sounds = it.data.toMutableList()
                is Resource.error -> view.context.toast(it.msg)
                Resource.loading -> {
                }
            }
        })

        viewModel.listLikedSounds()
    }

    override fun onPause() {
        playerMap.values.map {
            it.pause()
        }
        favoriteRV.setAllAsPaused()
        super.onPause()
    }

}