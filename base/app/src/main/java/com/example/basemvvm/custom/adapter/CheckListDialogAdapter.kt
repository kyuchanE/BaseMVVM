package com.example.basemvvm.custom.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basemvvm.R
import com.example.basemvvm.databinding.ItemDialogCheckListBinding
import com.example.basemvvm.utils.L
import com.example.basemvvm.utils.bind

class CheckListDialogAdapter (val context: Context, val selected: String = ""): RecyclerView.Adapter<CheckListDialogAdapter.CheckDialogHolder>() {

    private var itemList: MutableList<String> = mutableListOf()

    private var itemClickListener: (item: String, pos: Int) -> Unit = {_, _ -> }

    inner class CheckDialogHolder(
        private val binding: ItemDialogCheckListBinding
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bindViewHolder(pos: Int) {
            with(itemList[pos]) {

                binding.tvCheck.text = this
                binding.clCheckContainer.setOnClickListener {
                    L.d("CheckListDialogAdapter click")
                    itemClickListener(this, pos)
                }
                if (selected == this) {
                    binding.ivCheck.isSelected = true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckDialogHolder =
        CheckDialogHolder(parent.bind(R.layout.item_dialog_check_list))

    override fun onBindViewHolder(holder: CheckDialogHolder, position: Int) {
        holder.bindViewHolder(position)
    }

    override fun getItemCount(): Int = itemList.size

    fun addItemList(list: MutableList<String>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    // ClickEvent
    fun itemClickEvent(itemClickListener: (item: String, pos: Int) -> Unit) {
        this.itemClickListener = itemClickListener
    }
}