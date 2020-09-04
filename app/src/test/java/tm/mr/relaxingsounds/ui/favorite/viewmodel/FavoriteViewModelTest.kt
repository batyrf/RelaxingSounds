package tm.mr.relaxingsounds.ui.favorite.viewmodel

import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.Repository
import tm.mr.relaxingsounds.ui.BaseViewModelTest


@RunWith(MockitoJUnitRunner::class)
class  FavoriteViewModelTest: BaseViewModelTest() {

    @MockK
    lateinit var mockSoundRepository: Repository
    private lateinit var viewModel: FavoriteViewModel

    @MockK
    private lateinit var mockLiveDataObserver: Observer<Resource<List<Sound>>>
    private val mockSound = mockk<Sound>()

    override fun setUp() {
        super.setUp()
        viewModel = FavoriteViewModel(mockSoundRepository)
    }

    @Test
    fun `given soundRepository returns data, when listLikedSounds called, then update live data`() {
        val expectedData = listOf<Sound>()
        every { mockSoundRepository.getLikedSounds() } returns Observable.just(expectedData)

        viewModel.listLikedSounds()

        assert(viewModel.sounds.value is Resource.success)
        assertEquals(expectedData, (viewModel.sounds.value as Resource.success).data)
    }

    @Test
    fun `given soundRepository returns error, when listLikedSounds called, then update live data with error`() {
        val expectedMessage = "my error"
        every { mockSoundRepository.getLikedSounds() } returns Observable.error(Throwable(expectedMessage))

        viewModel.listLikedSounds()
        assert(viewModel.sounds.value is Resource.error)
        assertEquals(expectedMessage, (viewModel.sounds.value as Resource.error).msg)
    }

    @Test
    fun `given soundRepository completes, when updateSound called, then don't change anything on live data`() {
        every { mockSoundRepository.updateSound(mockSound) } returns Completable.complete()

        viewModel.sounds.observeForever(mockLiveDataObserver)

        assertNotNull(mockSound)
        viewModel.updateSound(mockSound)

        verify(exactly = 0) { mockLiveDataObserver.onChanged(any()) }
    }

    @Test
    fun `given soundRepository returns error, when updateSound called, then don't change anything on live data`() {
        every { mockSoundRepository.updateSound(mockSound) } returns Completable.error(Throwable())

        viewModel.sounds.observeForever(mockLiveDataObserver)

        viewModel.updateSound(mockSound)

        verify(exactly = 0) { mockLiveDataObserver.onChanged(any()) }
    }

}