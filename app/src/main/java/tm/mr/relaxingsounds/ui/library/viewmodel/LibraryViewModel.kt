package tm.mr.relaxingsounds.ui.library.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.repository.Repository

class LibraryViewModel @ViewModelInject constructor(
    private val repository: Repository
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