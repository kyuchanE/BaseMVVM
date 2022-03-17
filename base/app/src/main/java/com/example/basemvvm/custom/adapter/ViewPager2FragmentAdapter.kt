package com.example.basemvvm.custom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2FragmentAdapter (
    activity: FragmentActivity,
    private val items: List<Fragment>,
    private val action: (position: Int, item: Fragment) -> Unit = { _, _ -> }
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        action(position, items[position])
        return items[position]
    }

    override fun getItemCount() = items.size
}