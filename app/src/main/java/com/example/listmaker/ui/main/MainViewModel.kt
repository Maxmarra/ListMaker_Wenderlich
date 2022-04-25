package com.example.listmaker.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.listmaker.models.TaskList

// 1
class MainViewModel(private val sharedPreferences: SharedPreferences)
    : ViewModel() {
    // 2
    lateinit var onListAdded: (() -> Unit)

    // 3
    val lists: MutableList<TaskList> by lazy {
        retrieveLists()
    }

    // 4
    private fun retrieveLists(): MutableList<TaskList> {
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()
        for (taskList in sharedPreferencesContents) {
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)
            taskLists.add(list)
        }
        return taskLists
    }

    // 5
    fun saveList(list: TaskList) {
        sharedPreferences.edit().putStringSet(
            list.name,
            list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }
}