package tm.mr.relaxingsounds.ui.favorite.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.databinding.FavoriteSoundItemLayoutBinding
import tm.mr.relaxingsounds.ui.favorite.data.viewstate.FavoriteItemViewState

class FavoriteRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val favoriteAdapter = FavoriteAdapter()

    init {
        adapter = favoriteAdapter
        layoutManager = LinearLayoutManager(context)
    }

    fun setOnLikeClickListener(onLikeClick: (Sound) -> Unit) {
        favoriteAdapter.onLikeClick = onLikeClick
    }

    fun setOnPlayPauseClickListener(onPlayPauseClick: (Sound, Boolean) -> Unit) {
        favoriteAdapter.onPlayPauseClick = onPlayPauseClick
    }

    fun setOnVolumeChangeListener(onVolumeSet: (Sound, Int) -> Unit) {
        favoriteAdapter.onVolumeChange = onVolumeSet
    }

    fun setAllAsPaused() {
        favoriteAdapter.states.values.map {
            it.isPlaying = false
        }
        favoriteAdapter.notifyDataSetChanged()
    }

}

class FavoriteAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {

    var onLikeClick: ((Sound) -> Unit)? = null
    var onPlayPauseClick: ((Sound, Boolean) -> Unit)? = null
    var onVolumeChange: ((Sound, Int) -> Unit)? = null

    var sounds: MutableList<Sound> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    val states: MutableMap<String, FavoriteItemViewState> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder.create(parent)

    override fun getItemCount(): Int = sounds.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val sound = sounds[position]
        holder.bind(sound, states.getOrPut(sound.id) { FavoriteItemViewState(false, 4) }, {
            if (it.isLike != true) {
                notifyItemRemoved(sounds.indexOf(it))
                sounds.remove(it)
            }
            onLikeClick?.invoke(it)
        }, { sound, shouldPlay ->
            states.getOrPut(sound.id) { FavoriteItemViewState(false, 4) }.apply {
                isPlaying = shouldPlay
            }
            onPlayPauseClick?.invoke(sound, shouldPlay)
        }, { sound, volume ->
            states.getOrPut(sound.id) { FavoriteItemViewState(false, 4) }.apply {
                volumeLevel = volume
            }
            onVolumeChange?.invoke(sound, volume)
        })
    }

}

class FavoriteViewHolder(val binding: FavoriteSoundItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        sound: Sound,
        state: FavoriteItemViewState,
        onLikeClick: (Sound) -> Unit,
        onPlayPauseClick: (Sound, Boolean) -> Unit,
        onVolumeSet: (Sound, Int) -> Unit
    ) {
        binding.sound = sound
        binding.seekBar.progress
        binding.btnPlayPause.isSelected = state.isPlaying
        binding.executePendingBindings()
        binding.btnLike.setOnClickListener {
            it.isSelected = !it.isSelected
            sound.isLike = it.isSelected
            onLikeClick(sound)
        }
        binding.btnPlayPause.setOnClickListener {
            it.isSelected = !it.isSelected
            onPlayPauseClick(sound, it.isSelected)
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2)
                    onVolumeSet(sound, p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    companion object {

        fun create(parent: ViewGroup): FavoriteViewHolder {
            return FavoriteViewHolder(
                FavoriteSoundItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

}