package tm.mr.relaxingsounds.ui.list.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.databinding.ListSoundItemLayoutBinding

class ListRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    val listAdapter = ListViewAdapter()
    init {
        adapter = listAdapter
        layoutManager = LinearLayoutManager(context)
    }

    fun setOnItemClick(onItemClick: (Sound) -> Unit) {
        listAdapter.onItemClick = onItemClick
    }
}

class ListViewAdapter: PagingDataAdapter<Sound, ListViewHolder>(SOUND_COMPARATOR) {

    var onItemClick: ((Sound) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder.create(parent)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onItemClick ?: {})
        }
    }

    companion object {
        val SOUND_COMPARATOR = object : DiffUtil.ItemCallback<Sound>() {
            override fun areContentsTheSame(oldItem: Sound, newItem: Sound): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Sound, newItem: Sound): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: Sound, newItem: Sound): Any? {
                return null
            }
        }
    }

}

class ListViewHolder(private val binding: ListSoundItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(sound: Sound, onItemClick: (newSound: Sound) -> Unit) {
        binding.sound = sound
        binding.executePendingBindings()
        itemView.setOnClickListener {
            itemView.isSelected = !itemView.isSelected
            sound.isLike = itemView.isSelected
            onItemClick(sound)
        }
    }

    companion object {

        fun create(parent: ViewGroup) =
            ListViewHolder(
                ListSoundItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

}