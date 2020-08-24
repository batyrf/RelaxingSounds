package tm.mr.relaxingsounds.ui.library.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.databinding.CategoryItemLayoutBinding

class LibraryRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val libraryAdapter = LibraryAdapter()

    init {
        adapter = libraryAdapter
        layoutManager = LinearLayoutManager(context)
    }

    fun setItems(categories: List<Category>) {
        libraryAdapter.categories = categories
    }

    fun setOnItemClicked(onItemClicked: (Category) -> Unit) {
        libraryAdapter.onItemClicked = onItemClicked
    }

}


class LibraryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {

    var onItemClicked: ((Category) -> Unit)? = null

    var categories: List<Category> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder.create(parent)

    override fun getItemCount() =
        categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(categories.get(position), onItemClicked ?: {})

}

class CategoryViewHolder(private val binding: CategoryItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category, onItemClicked: (Category) -> Unit) {
        itemView.setOnClickListener {
            onItemClicked(category)
        }
        binding.category = category
        binding.executePendingBindings()
    }

    companion object {

        fun create(parent: ViewGroup) =
            CategoryViewHolder(
                CategoryItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

}