package tm.mr.relaxingsounds.ui.list.viewmodel

import androidx.lifecycle.Observer
import androidx.paging.PagingData
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Test
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.model.Sound
import tm.mr.relaxingsounds.data.repository.Repository
import tm.mr.relaxingsounds.ui.BaseViewModelTest

class ListViewModelTest: BaseViewModelTest() {

    @MockK
    lateinit var mockSoundRepository: Repository

    @MockK
    lateinit var mockLiveDataObserver: Observer<Resource<PagingData<Sound>>>

    private lateinit var viewModel: ListViewModel

    override fun setUp() {
        super.setUp()
        viewModel = ListViewModel(mockSoundRepository)
    }

    @Test
    fun `given soundRepository returns data, when getSounds called, then update live data`() {
        val mockCategoryId = "123"
        val expectedMockData = PagingData.from<Sound>(listOf())
        every { mockSoundRepository.getSounds(categoryId = mockCategoryId) } returns Observable.just(expectedMockData)

        viewModel.getSounds(categoryId = mockCategoryId)

        assert(viewModel.sounds.value is Resource.success)
        //todo will be finished later, getting pagingdatas
//        assertEquals(expectedMockData, (viewModel.sounds.value as Resource.success).data)
    }

    @Test
    fun `given soundRepository returns error, when getSounds called, then update live data with error`() {
        val mockCategoryId = "123"
        val expectedErrorMessage = "my error"
        every { mockSoundRepository.getSounds(categoryId = mockCategoryId) } returns Observable.error(Throwable(expectedErrorMessage))

        viewModel.getSounds(categoryId =  mockCategoryId)

        assert(viewModel.sounds.value is Resource.error)
        assertEquals(expectedErrorMessage, (viewModel.sounds.value as Resource.error).msg)
    }

    private val mockSound = mockk<Sound>()
    @Test
    fun `given soundRepository completes, when updateSound called, then don't change anything on sounds livedata`() {
        every { mockSoundRepository.updateSound(mockSound) } returns Completable.complete()

        viewModel.sounds.observeForever(mockLiveDataObserver)

        viewModel.updateSound(mockSound)
        verify(exactly = 0) { mockLiveDataObserver.onChanged(any()) }
    }

    @Test
    fun `given soundRepository returns error, when updateSound called, then don't change anything on sounds livedata`() {
        every { mockSoundRepository.updateSound(mockSound) } returns Completable.error(Throwable())

        viewModel.sounds.observeForever(mockLiveDataObserver)

        viewModel.updateSound(mockSound)
        verify(exactly = 0) { mockLiveDataObserver.onChanged(any()) }
    }

}