package co.uk.bbk.anacroitor

import androidx.room.*

@Dao
interface RecipeDao {

    // Insert a new recipe and replace existing entry in case of a conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    // Update existing recipe
    @Update
    suspend fun update(recipe: Recipe)

    // Delete existing recipe
    @Delete
    suspend fun delete(recipe: Recipe)

    // Get all recipes that are ordered by latest and added first
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    suspend fun getAllRecipes(): List<Recipe>

    // Fetch a specific recipe
    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): Recipe?
}