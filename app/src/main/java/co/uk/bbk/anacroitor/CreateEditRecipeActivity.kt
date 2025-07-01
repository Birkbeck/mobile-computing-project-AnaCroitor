package co.uk.bbk.anacroitor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class CreateEditRecipeActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeViewModel
    private var recipeId: Int? = null // For edit mode detection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_edit_recipe)

        // Handle edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ViewModel setup
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]

        // Get view references from layout
        val etRecipeName = findViewById<EditText>(R.id.etRecipeName)
        val etIngredients = findViewById<EditText>(R.id.etIngredients)
        val etInstructions = findViewById<EditText>(R.id.etInstructions)
        val btnSave = findViewById<Button>(R.id.btnSaveRecipe)
        val btnCancel = findViewById<Button>(R.id.btnCancelRecipe)
        val btnDelete = findViewById<Button>(R.id.btnDeleteRecipe)

        // Check if editing an existing recipe
        recipeId = intent.getIntExtra("RECIPE_ID", -1).takeIf { it != -1 }

        // If editing, load the recipe data into fields
        recipeId?.let { id ->
            viewModel.getRecipeById(id) { recipe ->
                recipe?.let {
                    etRecipeName.setText(it.title)
                    etIngredients.setText(it.ingredients)
                    etInstructions.setText(it.instructions)
                }
            }
        }

        // Handle Save button
        btnSave.setOnClickListener {
            val title = etRecipeName.text.toString().trim()
            val ingredients = etIngredients.text.toString().trim()
            val instructions = etInstructions.text.toString().trim()
            val category = "General" // Placeholder for future category support

            val recipe = Recipe(
                id = recipeId ?: 0,
                title = title,
                ingredients = ingredients,
                instructions = instructions,
                category = category
            )

            if (recipeId == null) viewModel.insert(recipe)
            else viewModel.update(recipe)

            finish()
        }

        // Optional: Cancel button just finishes the activity
        btnCancel.setOnClickListener {
            finish()
        }

        // Optional: Delete button only active if editing
        btnDelete.setOnClickListener {
            recipeId?.let { id ->
                viewModel.getRecipeById(id) { recipe ->
                    recipe?.let {
                        viewModel.delete(it)
                        finish()
                    }
                }
            }
        }
    }
}