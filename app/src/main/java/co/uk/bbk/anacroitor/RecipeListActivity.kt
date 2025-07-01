package co.uk.bbk.anacroitor

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeListActivity : AppCompatActivity() {

    private lateinit var viewModel: RecipeViewModel
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_list)

        // Handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize adapter with empty list and click handler
        adapter = RecipeAdapter(listOf()) { recipe ->
            // Open RecipeDetailsActivity when item is clicked
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id)
            startActivity(intent)
        }

        // Setup RecyclerView with adapter and layout manager
        val recyclerView = findViewById<RecyclerView>(R.id.rvRecipes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Initialize ViewModel and observe recipe list
        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        viewModel.recipeList.observe(this) { recipes ->
            adapter.updateList(recipes) // Update RecyclerView when data changes
        }

        // Trigger initial load
        viewModel.loadRecipes()

        // Add new recipe
        findViewById<FloatingActionButton>(R.id.fabAddRecipe).setOnClickListener {
            startActivity(Intent(this, CreateEditRecipeActivity::class.java))
        }
    }
}