package com.example.android.schoolist.data

import android.util.Log
import com.example.android.schoolist.model.Student
import com.google.firebase.firestore.FirebaseFirestore

class DataSource {

    // Access a Cloud Firestore instance from your Activity
    private val db = FirebaseFirestore.getInstance()

    fun loadStudentsList(groupName:String, successListener: DataFetched) {
        val studentsList = mutableListOf<Student>()

        db.collection("groups")
            .document(groupName)
            .collection("students")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    studentsList.add(document.toObject(Student::class.java))
                }
                successListener.onFetched(studentsList)
            }
            .addOnFailureListener { exception ->
                Log.d("download failed", "downloading failed", exception)
            }
    }

    interface DataFetched {
        fun onFetched(studentList: List<Student>)
    }
}