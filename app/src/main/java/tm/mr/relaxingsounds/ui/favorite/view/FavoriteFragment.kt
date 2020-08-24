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
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.ui.favorite.viewmodel.FavoriteViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sounds.observe(this as LifecycleOwner, Observer {
            favoriteRV.setOnLikeClickListener {
                viewModel.updateSound(it)
            }

            favoriteRV.setOnPlayPauseClickListener {

            }

            when (it) {
                is Resource.success -> {
                    (favoriteRV.adapter as? FavoriteAdapter)?.sounds = it.data.toMutableList()
                }
                is Resource.error -> {
                }
                Resource.loading -> {
                }
            }
        })

        viewModel.listLikedSounds()
    }

}