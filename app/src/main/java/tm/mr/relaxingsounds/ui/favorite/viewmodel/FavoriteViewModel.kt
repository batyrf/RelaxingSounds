package tm.mr.relaxingsounds.ui.favorite.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.Repository

class FavoriteViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val sounds: MutableLiveData<Resource<List<Sound>>> = MutableLiveData()

    fun listLikedSounds() {
        sounds.postValue(Resource.loading)
        viewModelScope.launch {
            sounds.postValue(repository.getLikedSounds())
        }
    }

    fun updateSound(sound: Sound) {
        viewModelScope.launch {
            try {
                repository.updateSound(sound)
            } catch (e: Exception) {}
        }
    }

}