package com.example.android.schoolist.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.schoolist.R
import com.example.android.schoolist.model.Student

class ItemAdapter(
    private val context: Context,
    private val clickListener: OnItemClick
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    var studentItemsList: List<Student> = emptyList()

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val lastNameTextView: TextView = view.findViewById(R.id.last_name_text)
        val nameTextView: TextView = view.findViewById(R.id.name_text)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = studentItemsList[position]
        holder.lastNameTextView.text = item.lastName
        holder.nameTextView.text = item.name
        holder.checkBox.text = "${position + 1}"

        holder.checkBox.isChecked = !item.status
        holder.lastNameTextView.isEnabled = item.status
        holder.nameTextView.isEnabled = item.status

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            clickListener.onItemCheckedChanged(position, isChecked)
            holder.lastNameTextView.isEnabled = !isChecked
            holder.nameTextView.isEnabled = !isChecked
        }
        /*holder.itemView.setOnClickListener {
         clickListener.onItemClickListener(position)
          }
      */
    }

    override fun getItemCount(): Int {
        return studentItemsList.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
        fun onItemCheckedChanged(position: Int, checked: Boolean)
    }
}