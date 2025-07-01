package co.uk.bbk.anacroitor

// Abstracts data access
class RecipeRepository(private val dao: RecipeDao) {

    // Inserts a new recipe into the database
    suspend fun insert(recipe: Recipe) {
        dao.insert(recipe)
    }

    // Updates an existing recipe
    suspend fun update(recipe: Recipe) {
        dao.update(recipe)
    }

    // Deletes a recipe from the database
    suspend fun delete(recipe: Recipe) {
        dao.delete(recipe)
    }

    // Returns all stored recipes
    suspend fun getAllRecipes(): List<Recipe> {
        return dao.getAllRecipes()
    }

    // Retrieves a recipe by its ID
    suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }
}