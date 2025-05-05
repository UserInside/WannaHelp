package com.example.wannahelp.presentation.searchScreen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(hostFragment: Fragment, private val list: List<Fragment>) :
    FragmentStateAdapter(hostFragment) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}
