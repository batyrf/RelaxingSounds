package tm.mr.relaxingsounds.ui.list.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.Repository

class ListViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val sounds: MutableLiveData<Resource<PagingData<Sound>>> = MutableLiveData()

    fun updateSound(sound: Sound) {
        viewModelScope.launch {
            repository.updateSound(sound)
        }
    }

    fun getSounds(categoryId: String) {
        sounds.postValue(Resource.loading)
        viewModelScope.launch {
            try {
                repository.getSounds(categoryId = categoryId)
                    .cachedIn(viewModelScope)
                    .collect {
                        sounds.postValue(Resource.success(it))
                    }
            } catch (e: Exception) {
                sounds.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

}