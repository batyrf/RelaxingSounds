package tm.mr.relaxingsounds.ui.library.viewmodel

import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.*
import tm.mr.relaxingsounds.data.model.Category
import tm.mr.relaxingsounds.data.model.Resource
import tm.mr.relaxingsounds.data.repository.Repository
import tm.mr.relaxingsounds.ui.BaseViewModelTest
import com.google.common.truth.Truth.assertThat

class LibraryViewModelTest : BaseViewModelTest() {

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
        coEvery { mockSoundRepository.getCategories() } returns Resource.success(expectedCategories)

        viewModel.getCategories()

        assertThat(viewModel.categories.value).isInstanceOf(Resource.success::class.java)
        assertThat((viewModel.categories.value as Resource.success).data)
            .isEqualTo(expectedCategories)
    }

    @Test
    fun `given soundRepository returns error, when getCategories called, then update live data with error resource`() {
        val expectedErrorMessage = "my error"
        coEvery { mockSoundRepository.getCategories() } returns Resource.error(expectedErrorMessage)

        viewModel.getCategories()

        assertThat(viewModel.categories.value).isInstanceOf(Resource.error::class.java)
        assertThat((viewModel.categories.value as Resource.error).msg)
            .isEqualTo(expectedErrorMessage)
    }

}