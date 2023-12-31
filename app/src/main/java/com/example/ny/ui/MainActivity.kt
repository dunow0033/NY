package com.example.ny.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ny.R
import com.example.ny.adapter.SchoolListAdapter
import com.example.ny.databinding.ActivityMainBinding
import com.example.ny.viewmodel.SchoolViewModel
import java.util.*

class MainActivity : AppCompatActivity()  //, SearchView.OnQueryTextListener {
{
    private lateinit var binding: ActivityMainBinding

    private var mSchoolViewModel: SchoolViewModel? = null
    private var Schooladapter: SchoolListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        mSchoolViewModel = ViewModelProvider(this).get(SchoolViewModel::class.java)

        // Update the cached copy of the words in the adapter.
        mSchoolViewModel!!.allSchools.observe(this) { list -> Schooladapter?.submitList(list) }

        mSchoolViewModel!!.loadSchools()
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() = binding.recyclerview.apply {
        Schooladapter = SchoolListAdapter(SchoolListAdapter.SchoolDiff())
        adapter = Schooladapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}

//    @SuppressLint("ResourceType")
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.layout.menu, menu)
//        val search = menu.findItem(R.id.action_search)
//        val searchView = search.actionView as SearchView
//        searchView.setOnQueryTextListener(this)
//        return true
//    }

 //   override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        if (id == R.id.menu_refresh) {
//            mSchoolViewModel?.loadSchools()
//            return true
//        } else if (id == R.id.action_search) {
//            val searchView = item.actionView as SearchView
//            searchView.setOnQueryTextListener(this)
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    override fun onQueryTextSubmit(query: String): Boolean {
//        return false
//    }

//    override fun onQueryTextChange(newText: String): Boolean {
//        // Updates Search filter and runs DB Query using search string non capitalized.
//        // We can additional for the entire Data instead
//        var newText = newText
//        newText = "%" + newText.lowercase(Locale.getDefault()) + "%"
//        mSchoolViewModel?.getFilteredSchools(newText)?.observe(this) { list -> adapter?.submitList(list) }
//        return true
//    }
