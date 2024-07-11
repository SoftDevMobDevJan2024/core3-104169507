package au.edu.swin.sdmd.myapp

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.edu.swin.sdmd.myapp.model.Group
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
  private lateinit var recyclerView : RecyclerView
  private lateinit var adapter: RecyclerListAdapter
  private lateinit var sortedGroups: List<Group>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val actionBar = supportActionBar
    actionBar!!.title = "Core 3"
    actionBar.subtitle = " Swinburne Club List"
    val groups = readGroups(this)
    sortedGroups = groups.sortedBy { it.dateTime }
    recyclerView = findViewById(R.id.recyclerView)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.setHasFixedSize(true)
    adapter = RecyclerListAdapter(sortedGroups)
    recyclerView.adapter = adapter

  }
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.all -> {
        adapter.updateData(sortedGroups)
        true
      }
      R.id.cultural -> {
        val filteredGroups = sortedGroups.filter { it.type == "Cultural" }
        adapter.updateData(filteredGroups)
        true
      }
      R.id.sport -> {
        val filteredGroups = sortedGroups.filter { it.type == "Sport" }
        adapter.updateData(filteredGroups)
        true
      }
      R.id.tech -> {
        val filteredGroups = sortedGroups.filter { it.type == "Tech" }
        adapter.updateData(filteredGroups)
        true
      }
      R.id.politics -> {
        val filteredGroups = sortedGroups.filter { it.type == "Politics" }
        adapter.updateData(filteredGroups)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
  // method to inflate the options menu when
  // the user opens the menu for the first time
  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu, menu)
    return super.onCreateOptionsMenu(menu)
  }
  private fun readGroups(context: Context): List<Group> {
    val inputStream = context.resources.openRawResource(R.raw.groups)
    val reader = BufferedReader(InputStreamReader(inputStream))
    val format = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm")
    val groups = mutableListOf<Group>()

    reader.useLines { lines ->
      lines.drop(1).forEach { line ->
        val cols = line.split(",")
        val id = cols[0].toInt()
        val name = cols[1]
        val location = cols[2]
        val type = cols[3]
        val dateTime = LocalDateTime.parse(cols[4], format)
        groups.add(Group(id, name, location, type, dateTime))
      }
    }
    return groups
  }
}