package co.uk.bbk.anacroitor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).recipeDao()
    private val repository = RecipeRepository(dao)

    val recipeList = MutableLiveData<List<Recipe>>()

    fun loadRecipes() {
        viewModelScope.launch {
            val data = repository.getAllRecipes()
            recipeList.postValue(data)
        }
    }

    fun insert(recipe: Recipe) {
        viewModelScope.launch {
            repository.insert(recipe)
            loadRecipes() // Refresh after insertion
        }
    }

    fun update(recipe: Recipe) {
        viewModelScope.launch {
            repository.update(recipe)
            loadRecipes()
        }
    }

    fun delete(recipe: Recipe) {
        viewModelScope.launch {
            repository.delete(recipe)
            loadRecipes()
        }
    }

    fun getRecipeById(id: Int, onResult: (Recipe?) -> Unit) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(id)
            onResult(recipe)
        }
    }
}