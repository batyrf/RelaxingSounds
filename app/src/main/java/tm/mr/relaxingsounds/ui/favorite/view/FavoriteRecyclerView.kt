package tm.mr.relaxingsounds.ui.favorite.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.databinding.FavoriteSoundItemLayoutBinding

class FavoriteRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    val favoriteAdapter = FavoriteAdapter()
    init {
        adapter = favoriteAdapter
        layoutManager = LinearLayoutManager(context)
    }

    fun setOnLikeClickListener(onLikeClick: (Sound) -> Unit) {
        favoriteAdapter.onLikeClick = onLikeClick
    }

    fun setOnPlayPauseClickListener(onPlayPauseClick: (Sound) -> Unit) {
        favoriteAdapter.onPlayPauseClick = onPlayPauseClick
    }

}

class FavoriteAdapter: RecyclerView.Adapter<FavoriteViewHolder>() {

    var onLikeClick: ((Sound) -> Unit)? = null
    var onPlayPauseClick: ((Sound) -> Unit)? = null

    var sounds: MutableList<Sound> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder.create(parent)

    override fun getItemCount(): Int = sounds.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(sounds[position], {
            if (it.isLike != true) {
                notifyItemRemoved(sounds.indexOf(it))
                sounds.remove(it)
            }
            onLikeClick?.invoke(it)
        }, onPlayPauseClick ?: {})
    }

}

class FavoriteViewHolder(private val binding: FavoriteSoundItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(sound: Sound, onLikeClick: (Sound) -> Unit, onPlayPauseClick: (Sound) -> Unit) {
        binding.sound = sound
        binding.executePendingBindings()
        binding.btnLike.setOnClickListener {
            it.isSelected = !it.isSelected
            sound.isLike = it.isSelected
            onLikeClick(sound)
        }
        binding.btnPlayPause.setOnClickListener {
            it.isSelected = !it.isSelected
            onPlayPauseClick(sound)
        }
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