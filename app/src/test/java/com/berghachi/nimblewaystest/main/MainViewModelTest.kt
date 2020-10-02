package com.berghachi.nimblewaystest.main



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.berghachi.nimblewaystest.data.local.model.Repo
import com.berghachi.nimblewaystest.data.repository.Repository
import com.berghachi.nimblewaystest.ui.main.MainViewModel
import com.berghachi.nimblewaystest.utils.Resource
import com.berghachi.nimblewaystest.utils.RxImmediateSchedulerRule
import com.berghachi.nimblewaystest.utils.getOrAwaitValue
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: Repository
    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup(){

        MockitoAnnotations.initMocks(this)
        mainViewModel= MainViewModel(repository = mockRepository)
    }


    @Test
    fun ` test case when getRepos succes `() {
        val page = 1

        val expectedList = arrayListOf(Repo(),Repo(),Repo(),Repo())
        Mockito.`when`(mockRepository.getRepos(page)).thenReturn(
            Single.just(expectedList))

       val result = mainViewModel.getRepos(page).getOrAwaitValue {}

        Mockito.verify(mockRepository).getRepos(page)
        assertEquals(Resource.success(expectedList),result)

    }

    @Test
    fun ` test case when getRepos failed  `() {
        val page = 1
        Mockito.`when`(mockRepository.getRepos(page)).thenReturn(
            Single.error(
                HttpException(
                    Response.error<Any>(500, ResponseBody.create("text/plain".toMediaTypeOrNull(), "{\n" +
                    "  \"message\": \"message\",\n" +
                    "  \"body\": {}\n" +
                    "}")))
            ))


        val result = mainViewModel.getRepos(page).getOrAwaitValue {}

        Mockito.verify(mockRepository).getRepos(page)

        assertEquals(Resource.error("message",false),result)

    }
}