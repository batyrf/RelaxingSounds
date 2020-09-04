package tm.mr.relaxingsounds.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseViewModelTest {

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
        clearAllMocks()
    }

}