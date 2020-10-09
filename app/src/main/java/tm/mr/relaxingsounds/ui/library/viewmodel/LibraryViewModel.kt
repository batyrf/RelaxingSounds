package tm.mr.relaxingsounds.ui.library.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.repository.Repository

class LibraryViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()

    fun getCategories() {
        categories.postValue(Resource.loading)
        viewModelScope.launch {
            categories.postValue(repository.getCategories())
        }
    }

}