package com.example.wannahelp.profileScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {
    private lateinit var binding: FragmentProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.action_profile

        val recyclerView = binding.profileInformation.recyclerViewYourFriends

        val friendsList =
            listOf(
                FriendCard(R.drawable.avatar_3, "Дмитрий Валериевич"),
                FriendCard(R.drawable.avatar_2, "Евгений Александров"),
                FriendCard(R.drawable.avatar_1, "Виктор Кузнецов"),
            )

        val adapter = FriendsRecyclerViewAdapter(friendsList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        binding.appBar.toolbar.setupWithNavController(findNavController())

        val toolbar = binding.appBar.toolbar

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_confirm -> {
                NavHostFragment.findNavController(this).navigate(R.id.navigateToEditProfileScreen)
                    true
                }
                else -> false
            }
        }
    }
}
