package co.uk.bbk.anacroitor

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room entity which represents a recipe entry
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-incremented primary key
    val title: String,
    val ingredients: String,
    val instructions: String,
    val category: String // Category: Breakfast, Brunch, Lunch etc.
)
