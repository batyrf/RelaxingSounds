package tm.mr.relaxingsounds.ui.library.viewmodel

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import org.junit.*

import org.junit.Assert.*
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.repository.Repository
import tm.mr.relaxingsounds.ui.BaseViewModelTest

class LibraryViewModelTest: BaseViewModelTest() {

    @MockK
    private lateinit var mockSoundRepository: Repository
    private lateinit var viewModel: LibraryViewModel

    override fun setUp() {
        super.setUp()
        viewModel = LibraryViewModel(mockSoundRepository)
    }

    @Test
    fun `given soundRepository returns data, when getCategories called, then update live data`() {
        val expectedCategories = listOf<Category>()
        every { mockSoundRepository.getCategories() } returns Observable.just(expectedCategories)

        viewModel.getCategories()

        assertTrue(viewModel.categories.value is Resource.success)
        assertEquals(expectedCategories, (viewModel.categories.value as Resource.success).data)
    }

    @Test
    fun `given soundRepository returns error, when getCategories called, then update live data with error resource`() {
        val expectedError = Throwable("my error")
        every { mockSoundRepository.getCategories() } returns Observable.error(expectedError)

        viewModel.getCategories()

        assertTrue(viewModel.categories.value is Resource.error)
        assertEquals(expectedError.message, (viewModel.categories.value as Resource.error).msg)
    }

}