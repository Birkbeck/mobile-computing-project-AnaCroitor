package co.uk.bbk.anacroitor

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class RecipeRepositoryTest {

    // Mocked repository and the DAO under test
    private lateinit var dao: RecipeDao
    private lateinit var repository: RecipeRepository

    // Sample recipe
    private val testRecipe = Recipe(id = 1, title = "Pancakes", ingredients = "Flour, Eggs", instructions = "Mix & fry", category = "Breakfast")

    @Before
    fun setup() {
        // Initialize mock DAO and inject into repository
        dao = mock(RecipeDao::class.java)
        repository = RecipeRepository(dao)
    }

    @Test
    fun testInsertRecipe() = runBlocking {
        // Call insert and verify that DAO's insert was called with correct argument
        repository.insert(testRecipe)
        verify(dao).insert(testRecipe)
    }

    @Test
    fun testUpdateRecipe() = runBlocking {
        // Call update and verify that DAO's update was triggered
        repository.update(testRecipe)
        verify(dao).update(testRecipe)
    }

    @Test
    fun testDeleteRecipe() = runBlocking {
        // Call delete and confirm DAO's delete method was called
        repository.delete(testRecipe)
        verify(dao).delete(testRecipe)
    }

    @Test
    fun testGetById() = runBlocking {
        // Assert the returned recipe matches the expected one
        `when`(dao.getRecipeById(1)).thenReturn(testRecipe)
        val result = repository.getRecipeById(1)
        assertEquals(testRecipe, result)
    }
}