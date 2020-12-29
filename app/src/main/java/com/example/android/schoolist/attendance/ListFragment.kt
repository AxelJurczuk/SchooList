package com.example.android.schoolist.attendance

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.schoolist.R
import com.example.android.schoolist.data.DataSource
import com.example.android.schoolist.data.ItemAdapter
import com.example.android.schoolist.databinding.FragmentListBinding
import com.example.android.schoolist.extensions.hide
import com.example.android.schoolist.extensions.show
import com.example.android.schoolist.model.Student
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter

class ListFragment : Fragment(), ItemAdapter.OnItemClick {

    companion object {
        private const val NAME = "NAME"

        //se llama cuando se crea el fragment, en este caso quiero que se cree con parametros
        fun newInstance(groupName: String): Fragment {

            val newListFragment = ListFragment()
            val args = Bundle()
            args.putString(NAME, groupName)
            newListFragment.arguments = args

            return newListFragment
        }
    }

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var listener: OnShowGroupListener
    private val db = FirebaseFirestore.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnShowGroupListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = ItemAdapter(requireContext(), this@ListFragment)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        if (recyclerView != null) {
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }
        val groupName = arguments?.getString(NAME).toString()
        activity?.title = groupName

        //loading list
        DataSource().loadStudentsList(groupName, object : DataSource.DataFetched {
            override fun onFetched(studentList: List<Student>) {
                adapter.studentItemsList = studentList
                adapter.notifyDataSetChanged()
                binding.btnSaveAbsents.isEnabled = true
                binding.progressBar.hide()
            }
        })

        val date: LocalDate = now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val parsedDate = date.format(formatter).toString()
        Log.d("date", parsedDate)
        //reference for current group

        //save absents
        binding.btnSaveAbsents.setOnClickListener {

            binding.progressBar.show()
            val studentsList = adapter.studentItemsList
            val absentList = studentsList.filter { it.status == false }
            val mutableStudentsMap = mutableMapOf<String, Any>()
            var docReference = 0

            absentList.forEach { student ->
                mutableStudentsMap.putAll(convertToMap(student))
                docReference++
                db.collection("ausentes")
                    .document(parsedDate)
                    .collection(groupName)
                    .document(docReference.toString())
                    .set(mutableStudentsMap)
                    .addOnSuccessListener {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            "Absent list saved!",
                            Toast.LENGTH_SHORT
                        ).show()
                        //leave current fragment

                    }
                    .addOnFailureListener {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            "Error saving document",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
        //search by date
        binding.btnSearchByDate.setOnClickListener {
            listener.showGroupQuery(parsedDate, groupName)
        }
        /*
       TODO :
       que pasa si es 1era vez y no hay conexion
    */
    }

    private fun convertToMap(student: Student): Map<String, Any> {
        return mapOf(
            "lastName" to student.lastName,
            "name" to student.name,
            "status" to student.status
        )
    }

    override fun onItemCheckedChanged(position: Int, checked: Boolean) {
        adapter.studentItemsList[position].status = checked
    }

    override fun onItemClickListener(position: Int) {
        TODO("Not yet implemented")
    }

}