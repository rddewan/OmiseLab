package com.richarddewan.omiselab.ui.Charity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.data.remote.response.CharityResponse
import com.richarddewan.omiselab.data.remote.response.CharityResponseList
import com.richarddewan.omiselab.data.repository.CharityRepository
import com.richarddewan.omiselab.util.getOrAwaitValue
import com.richarddewan.omiselab.util.network.NetworkHelper
import com.richarddewan.omiselab.util.rx.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.Matchers.`is`
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharityViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper
    @Mock
    private lateinit var charityRepository: CharityRepository
    //rx java test scheduler
    private lateinit var testScheduler: TestScheduler
    //subject under test
    private lateinit var viewModel: CharityViewModel

    @Before
    fun setUp() {
        val compositeDisposable = CompositeDisposable()
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        viewModel = CharityViewModel(
            compositeDisposable,
            testSchedulerProvider,
            networkHelper,
            charityRepository
        )
    }

    @Test
    fun givenNoInternetConnection_whenGetCharityList_returnNetworkError(){

        doReturn(false)
            .`when`(networkHelper)
            .isNetworkConnected()

        viewModel.getCharityList()
        //run the test scheduler
        testScheduler.triggerActions()

        assertThat(viewModel.messageStringId.getOrAwaitValue(), `is`(R.string.network_connection_error))
        assertThat(viewModel.isLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun givenServerResponseOK_whenGetCharityList_returnCharityList(){
        val mList = listOf<CharityResponseList>(
            CharityResponseList(0, "Ban Khru Noi", "http://rkdretailiq.com/news/img-corporate-baankrunoi.jpg"),
            CharityResponseList(1, "Habitat for Humanity Thailand", "http://www.adamandlianne.com/uploads/2/2/1/6/2216267/3231127.gif"),
            CharityResponseList(3, "Makhampom", "http://www.makhampom.net/makhampom/ppcms/uploads/UserFiles/Image/Thai/T14Publice/2554/January/Newyear/logoweb.jpg")
        )
        val response = CharityResponse(3,mList)

        doReturn(true)
            .`when`(networkHelper)
            .isNetworkConnected()

        doReturn(Single.just(response))
            .`when`(charityRepository)
            .getCharity()

        viewModel.getCharityList()
        //run the test scheduler
        testScheduler.triggerActions()

        assertThat(viewModel.isLoading.getOrAwaitValue(), `is`(false))
        assertThat(viewModel.charirtyList.getOrAwaitValue().size, `is`(3))

    }
}