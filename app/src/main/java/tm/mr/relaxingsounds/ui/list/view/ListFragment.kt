package tm.mr.relaxingsounds.ui.list.view

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tm.mr.relaxingsounds.R

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    val args: ListFragmentArgs by navArgs()

}