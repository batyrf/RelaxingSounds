package tm.mr.relaxingsounds.ui.library.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_library.*
import tm.mr.relaxingsounds.R

@AndroidEntryPoint
class LibraryFragment : Fragment(R.layout.fragment_library) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn.setOnClickListener {
            val action = LibraryFragmentDirections.actionLibraryFragmentToListFragment()
            it.findNavController().navigate(action)
        }
    }

}