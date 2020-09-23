package com.kopa.me.driver.viewmodel.user

import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class UserHistoryViewModelTest {

    /*private val repository: IUserHistoryRepository = mockk(relaxed = true)
    private val repoSuccessStateGet: SimpleResult<List<UserHistoryRecord>> =
        ResultState.Success(listOf(UserHistoryRecord(1)))
    private val repoFailureStateGet = ResultState.Failure(Exception("Error"))
    private val repoSuccessStateUnit: SimpleResult<Unit> = ResultState.Success(Unit)


    private lateinit var viewModel: UserHistoryViewModel
    private val historyStateObserver = mockk<Observer<UserHistoryViewState>>(relaxed = true)

    private val userHistoryRecord = UserHistoryRecord(id = 1, filledLiters = 1.0)


    private val loadingState = UserHistoryViewState.Loading
    private val initState = UserHistoryViewState.Init(listOf(UserHistoryRecord(1)))
    private val paginateState = UserHistoryViewState.Paginate(listOf(UserHistoryRecord(1)))
    private val insertNewOneState = UserHistoryViewState.InsertNewOne(listOf(userHistoryRecord))


    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.loadUserHistory(null) } returns repoSuccessStateGet
        coEvery { repository.loadUserHistory(10) } returns repoSuccessStateGet
        coEvery { repository.loadUserHistory(-1) } returns repoFailureStateGet

        coEvery { repository.addUserHistoryRecord(userHistoryRecord) } returns repoSuccessStateUnit

        viewModel = UserHistoryViewModel(repository)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun findUserStationBySlug() {

    }

    @Test
    fun clearInputFields() {

    }

    @Test
    fun addHistoryRecord() = runBlocking {
        viewModel.userHistoryState.observeForever(historyStateObserver)
        viewModel.addHistoryRecord(userHistoryRecord)
        coVerify { repository.addUserHistoryRecord(userHistoryRecord) }
        coVerify {
            historyStateObserver.onChanged(insertNewOneState)
        }
    }


    @Test
    fun getHistoryRecords() = runBlocking {
        viewModel.userHistoryState.observeForever(historyStateObserver)

        //init
        viewModel.getHistoryRecords(null)
        coVerify { repository.loadUserHistory(null) }
        delay(10)
        coVerifyOrder {
            historyStateObserver.onChanged(loadingState)
            historyStateObserver.onChanged(initState)
        }

        //paginate
        viewModel.getHistoryRecords(10)
        coVerify { repository.loadUserHistory(10) }
        delay(10)
        coVerifyOrder {
            historyStateObserver.onChanged(loadingState)
            historyStateObserver.onChanged(paginateState)
        }
    }*/
}