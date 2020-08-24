package tm.mr.relaxingsounds.component.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import tm.mr.relaxingsounds.R

class LikableButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    init {
        val drawable = ContextCompat.getDrawable(context, R.drawable.likable_button)
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("liked")
        fun like(view: LikableButton, isLiked: Boolean?) {
            view.isSelected = isLiked ?: false
        }

    }

}