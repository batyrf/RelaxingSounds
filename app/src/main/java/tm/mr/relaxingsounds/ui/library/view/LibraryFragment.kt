package tm.mr.relaxingsounds.ui.library.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_library.*
import tm.mr.relaxingsounds.R
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.ui.library.viewmodel.LibraryViewModel

@AndroidEntryPoint
class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val libraryViewModel: LibraryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        libraryRV.setOnItemClicked {
            val action = LibraryFragmentDirections.actionLibraryFragmentToListFragment(it.id)
            this@LibraryFragment.findNavController().navigate(action)
        }

        libraryViewModel.categories
            .observe(this as LifecycleOwner, Observer {
                when (it) {
                    is Resource.success -> libraryRV.setItems(it.data)
                    is Resource.error -> {
                    }
                    Resource.loading -> {
                    }
                }
            })
        libraryViewModel.getCategories()
    }

}