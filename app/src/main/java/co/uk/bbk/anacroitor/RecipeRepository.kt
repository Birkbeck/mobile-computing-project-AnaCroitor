package co.uk.bbk.anacroitor

class RecipeRepository(private val dao: RecipeDao) {

    suspend fun insert(recipe: Recipe) {
        dao.insert(recipe)
    }

    suspend fun update(recipe: Recipe) {
        dao.update(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        dao.delete(recipe)
    }

    suspend fun getAllRecipes(): List<Recipe> {
        return dao.getAllRecipes()
    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }
}