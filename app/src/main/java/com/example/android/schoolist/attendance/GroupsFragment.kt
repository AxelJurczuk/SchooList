package com.example.android.schoolist.attendance

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.schoolist.R
import com.example.android.schoolist.databinding.FragmentGroupsBinding

class GroupsFragment : Fragment() {

    private lateinit var binding:FragmentGroupsBinding
    private lateinit var listener: OnShowGroupListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnShowGroupListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentGroupsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnFirstYear.setOnClickListener {
            listener.showGroupInfo("group1")
        }
        binding.btnSecondYear.setOnClickListener {
            listener.showGroupInfo("group2")
        }
        binding.btnThirdYear.setOnClickListener {
            listener.showGroupInfo("group3")
        }
        binding.btnFourthYear.setOnClickListener {
            listener.showGroupInfo("group4")
        }
        binding.btnFifthYear.setOnClickListener {
            listener.showGroupInfo("group5")
        }
    }

}