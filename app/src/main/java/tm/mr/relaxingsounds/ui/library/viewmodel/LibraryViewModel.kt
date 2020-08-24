package tm.mr.relaxingsounds.ui.library.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.repository.SoundRepository

class LibraryViewModel @ViewModelInject constructor(
    private val repository: SoundRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()

    fun getCategories() {
        categories.postValue(Resource.loading)
        repository.getCategories()
            .subscribe(
                {
                    categories.postValue(Resource.success(it))
                },
                {
                    categories.postValue(Resource.error(it.message ?: ""))
                }
            )
            .addTo(CompositeDisposable())
    }

}