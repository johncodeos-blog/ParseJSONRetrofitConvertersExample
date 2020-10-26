package com.example.parsejsonretrofitgsonexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell.view.*

class RVAdapter(private val cell: ArrayList<Cell>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.cell, parent, false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.employee_id_textview.text = cell[position].employeeId
        holder.itemView.employee_name_textview.text = cell[position].employeeName
        holder.itemView.employee_salary_textview.text = cell[position].employeeSalary
        holder.itemView.employee_age_textview.text = cell[position].employeeAge
    }

    override fun getItemCount(): Int {
        return cell.size
    }
}