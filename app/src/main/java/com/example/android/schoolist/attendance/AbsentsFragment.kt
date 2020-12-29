package com.example.android.schoolist.attendance

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.schoolist.R
import com.example.android.schoolist.data.DataSource
import com.example.android.schoolist.data.ItemAdapter
import com.example.android.schoolist.databinding.FragmentAbsentsBinding
import com.example.android.schoolist.extensions.hide
import com.example.android.schoolist.extensions.show
import com.example.android.schoolist.model.Student
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AbsentsFragment : Fragment(), ItemAdapter.OnItemClick {

    companion object{
        private const val GROUPNAME = "GROUPNAME"
        private const val DATE = "DATE"

        fun newInstance (date:String, groupName:String): Fragment{

            val newFragment = AbsentsFragment()
            val args= Bundle()
            args.putString(DATE, date)
            args.putString(GROUPNAME,groupName)
            newFragment.arguments= args
            return newFragment
        }
    }

    private lateinit var binding:FragmentAbsentsBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var listener: OnShowGroupListener
    private val db= FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentAbsentsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter= ItemAdapter(requireContext(), this@AbsentsFragment)
        val recyclerView= binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        binding.etPickDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnSearch.setOnClickListener {
            binding.progressBar.show()
            val date = binding.etPickDate.text.toString()
            val groupName = arguments?.getString(GROUPNAME).toString()

            DataSource().loadAbsentStudents(date, groupName, object : DataSource.DataFetched {
                override fun onFetched(studentList: List<Student>) {
                    binding.progressBar.hide()
                    adapter.studentItemsList = studentList
                    adapter.notifyDataSetChanged()
                    Log.d("students", studentList.toString())
                }
            })
        }

    }
    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // +1 because January is zero
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val selectedDate = LocalDate.of(year, month + 1, day).format(formatter)

                binding.etPickDate.setText(selectedDate)
                binding.btnSearch.isEnabled = true
            })
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun onItemClickListener(position: Int) {

    }

    override fun onItemCheckedChanged(position: Int, checked: Boolean) {

    }

}