package tm.mr.relaxingsounds.ui.favorite.viewmodel

import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.Repository
import tm.mr.relaxingsounds.ui.BaseViewModelTest
import com.google.common.truth.Truth.assertThat

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest : BaseViewModelTest() {

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
        coEvery { mockSoundRepository.getLikedSounds() } returns Resource.success(expectedData)

        viewModel.listLikedSounds()

        assertThat(viewModel.sounds.value).isInstanceOf(Resource.success::class.java)
        assertThat((viewModel.sounds.value as Resource.success).data).isEqualTo(expectedData)
    }

    @Test
    fun `given soundRepository returns error, when listLikedSounds called, then update live data with error`() {
        val expectedMessage = "my error"
        coEvery { mockSoundRepository.getLikedSounds() } returns Resource.error(expectedMessage)

        viewModel.listLikedSounds()
        assertThat(viewModel.sounds.value).isInstanceOf(Resource.error::class.java)
        assertThat((viewModel.sounds.value as Resource.error).msg).isEqualTo(expectedMessage)
    }

    @Test
    fun `given soundRepository completes, when updateSound called, then don't change anything on live data`() {
        coEvery { mockSoundRepository.updateSound(mockSound) }

        viewModel.sounds.observeForever(mockLiveDataObserver)

        assertThat(mockSound).isNotNull()
        viewModel.updateSound(mockSound)

        verify(exactly = 0) { mockLiveDataObserver.onChanged(any()) }
    }

    @Test
    fun `given soundRepository returns error, when updateSound called, then don't change anything on live data`() {
        coEvery { mockSoundRepository.updateSound(mockSound) } throws Throwable()

        viewModel.sounds.observeForever(mockLiveDataObserver)

        viewModel.updateSound(mockSound)

        verify(exactly = 0) { mockLiveDataObserver.onChanged(any()) }
    }

}