package co.uk.bbk.anacroitor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Manages recipe data
class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    // Create repository
    private val dao = AppDatabase.getInstance(application).recipeDao()
    private val repository = RecipeRepository(dao)

    // LiveData observable list of all recipes
    val recipeList = MutableLiveData<List<Recipe>>()

    // Fetch all recipes and update LiveData
    fun loadRecipes() {
        viewModelScope.launch {
            val data = repository.getAllRecipes()
            recipeList.postValue(data)
        }
    }

    // Insert a recipe and refresh list
    fun insert(recipe: Recipe) {
        viewModelScope.launch {
            repository.insert(recipe)
            loadRecipes() // Refresh after insertion
        }
    }

    // Update a recipe and refresh list
    fun update(recipe: Recipe) {
        viewModelScope.launch {
            repository.update(recipe)
            loadRecipes()
        }
    }

    // Delete a recipe and refresh list
    fun delete(recipe: Recipe) {
        viewModelScope.launch {
            repository.delete(recipe)
            loadRecipes()
        }
    }

    // Get a recipe by ID and return result via callback
    fun getRecipeById(id: Int, onResult: (Recipe?) -> Unit) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(id)
            onResult(recipe)
        }
    }
}