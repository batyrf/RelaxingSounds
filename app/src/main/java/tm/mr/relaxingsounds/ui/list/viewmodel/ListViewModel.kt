package tm.mr.relaxingsounds.ui.list.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.SoundRepository

class ListViewModel @ViewModelInject constructor(
    private val repository: SoundRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val sounds: MutableLiveData<Resource<PagingData<Sound>>> = MutableLiveData()

    fun updateSound(sound: Sound) {
        repository.updateSound(sound)
            .subscribe({}, {})
            .addTo(CompositeDisposable())
    }

    fun getSounds(categoryId: String) {
        sounds.postValue(Resource.loading)
        repository.getSounds(categoryId = categoryId)
            .cachedIn(viewModelScope)
            .subscribe(
                {
                    sounds.postValue(Resource.success(it))
                },
                {
                    sounds.postValue(Resource.error(it.localizedMessage ?: ""))
                }
            )
            .addTo(CompositeDisposable())
    }

}