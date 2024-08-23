package com.example.expense_management_mvvm.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    private val bindingInflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val dataList: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<BaseViewHolder<VB>>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bindData(holder.binding, dataList[position], position)
        onItemClick(holder.binding, dataList[position], position)
    }

    abstract fun bindData(binding: VB, item: T, position: Int)
    abstract fun onItemClick(binding: VB, item: T, position: Int)

    fun setData(position: Int, data: T) {
        if (position > dataList.size) {
            return
        }
        dataList[position] = data
       notifyItemChanged(position)
    }

    fun removeData(position: Int){
        if (position > dataList.size){
            return
        }
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData(){
        dataList.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(list : MutableList<T>){
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater =LayoutInflater.from(parent.context)
        val binding = bindingInflate(inflater, parent, false)
        return BaseViewHolder(binding)
    }
}