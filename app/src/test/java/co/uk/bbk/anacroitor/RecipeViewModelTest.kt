package co.uk.bbk.anacroitor

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {

    // Forces LiveData to behave synchronously in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: RecipeRepository
    private lateinit var application: Application
    private lateinit var viewModel: RecipeViewModel

    private val testRecipe = Recipe(
        id = 1,
        title = "Test",
        ingredients = "Test",
        instructions = "Test",
        category = "General"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mock()
        repository = mock()
        viewModel = RecipeViewModel(application, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoadRecipes_updatesLiveData() = runTest {
        val recipes = listOf(testRecipe)
        `when`(repository.getAllRecipes()).thenReturn(recipes)

        val observer: Observer<List<Recipe>> = mock()
        viewModel.recipeList.observeForever(observer)

        viewModel.loadRecipes()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(observer).onChanged(recipes)
    }

    @Test
    fun testInsert_invokesRepository() = runTest {
        // Only call insert
        `when`(repository.getAllRecipes()).thenReturn(listOf(testRecipe))

        viewModel.insert(testRecipe)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).insert(testRecipe)
        verify(repository).getAllRecipes()
    }

    @Test
    fun testDelete_invokesRepository() = runTest {
        `when`(repository.getAllRecipes()).thenReturn(emptyList())

        viewModel.delete(testRecipe)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(repository).delete(testRecipe)
        verify(repository).getAllRecipes()
    }

    @Test
    fun testGetById_returnsCorrectItem() = runTest {
        `when`(repository.getRecipeById(1)).thenReturn(testRecipe)

        var result: Recipe? = null
        viewModel.getRecipeById(1) {
            result = it
        }

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(testRecipe, result)
    }
}