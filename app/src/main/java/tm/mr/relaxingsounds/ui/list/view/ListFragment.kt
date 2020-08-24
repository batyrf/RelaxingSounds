package tm.mr.relaxingsounds.ui.list.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import tm.mr.relaxingsounds.R
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.ui.list.viewmodel.ListViewModel

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val args: ListFragmentArgs by navArgs()
    private val listViewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listRV.setOnItemClick {
            listViewModel.updateSound(it)
        }

        listViewModel.sounds.observe(this as LifecycleOwner, Observer {
            when (it) {
                is Resource.success -> {
                    (listRV.adapter as? ListViewAdapter)?.submitData(lifecycle, it.data)
                }
                is Resource.error -> {
                }
                Resource.loading -> {
                }
            }
        })

        listViewModel.getSounds(args.categoryId)
    }

}