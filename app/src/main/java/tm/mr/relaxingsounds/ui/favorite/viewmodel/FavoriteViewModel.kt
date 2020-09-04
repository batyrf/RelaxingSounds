package tm.mr.relaxingsounds.ui.favorite.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.Repository

class FavoriteViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val sounds: MutableLiveData<Resource<List<Sound>>> = MutableLiveData()

    fun listLikedSounds() {
        sounds.postValue(Resource.loading)
        repository.getLikedSounds()
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

    fun updateSound(sound: Sound) {
        repository.updateSound(sound)
            .subscribe({}, {})
            .addTo(CompositeDisposable())
    }

}