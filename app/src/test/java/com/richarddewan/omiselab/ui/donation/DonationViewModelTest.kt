package com.richarddewan.omiselab.ui.donation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.richarddewan.omiselab.R
import com.richarddewan.omiselab.data.remote.request.DonationRequest
import com.richarddewan.omiselab.data.remote.response.DonationResponse
import com.richarddewan.omiselab.data.repository.DonationRepository
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
class DonationViewModelTest {

    /*
    run architecture component related background jobs in same thread for testing
     */
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper
    @Mock
    private lateinit var donationRepository: DonationRepository
    //rx java test scheduler
    private lateinit var testScheduler: TestScheduler
    //subject under test
    private lateinit var viewModel: DonationViewModel


    @Before
    fun setUp() {
        val compositeDisposable = CompositeDisposable()
        testScheduler = TestScheduler()
        val testScheduleProvider = TestSchedulerProvider(testScheduler)
        viewModel = DonationViewModel(
            compositeDisposable,
            testScheduleProvider,
            networkHelper,
            donationRepository
        )
    }

    @Test
    fun giveNoInternetConnection_whenSendDonation_returnNetworkError(){
        val request = DonationRequest("John Smith","token_test_123",100.20)

        doReturn(false)
            .`when`(networkHelper)
            .isNetworkConnected()

        viewModel.sendDonation(request)
        //run the test scheduler
        testScheduler.triggerActions()

        assertThat(viewModel.messageStringId.getOrAwaitValue(), `is`(R.string.network_connection_error))
        assertThat(viewModel.isLoading.getOrAwaitValue(), `is`(false))


    }

    @Test
    fun givenValidDonationRequest_whenSendDonation_returnSuccessResponse(){
        val request = DonationRequest("John Smith","token_test_123",100.20)
        val response = DonationResponse(true,"ino error","Success")

        doReturn(true)
            .`when`(networkHelper)
            .isNetworkConnected()

        doReturn(Single.just(response))
            .`when`(donationRepository)
            .addDonation(request)

        viewModel.sendDonation(request)
        //run the test scheduler
        testScheduler.triggerActions()

        assertThat(viewModel.donationResponse.getOrAwaitValue().success, `is`(true))
        assertThat(viewModel.isSuccess.getOrAwaitValue(), `is`(true))
        assertThat(viewModel.isLoading.getOrAwaitValue(), `is`(false))


    }
}