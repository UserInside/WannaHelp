package com.example.wannahelp.profileScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentProfileScreenBinding
import kotlinx.coroutines.launch

class ProfileScreenFragment : Fragment() {
    private lateinit var binding: FragmentProfileScreenBinding
    private val viewModel : ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val customToolbar = binding.appBar.profileToolbar
        customToolbar.apply {
            title = getString(R.string.profile)
            addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(
                        menu: Menu,
                        menuInflater: MenuInflater,
                    ) {
                        menuInflater.inflate(R.menu.menu_toolbar_profile, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        return when (menuItem.itemId) {
                            R.id.action_edit_profile -> {
                                NavHostFragment.findNavController(this@ProfileScreenFragment)
                                    .navigate(R.id.navigateToEditProfileScreen)
                                true
                            }

                            else -> false
                        }
                    }
                },
                viewLifecycleOwner,
                Lifecycle.State.STARTED,
            )
        }

        val recyclerView = binding.profileInformation.recyclerViewYourFriends

        lifecycleScope.launch {
            viewModel.friendsList.collect { friendsList ->
                val rvAdapter = FriendsRecyclerViewAdapter(friendsList)
                with(recyclerView) {
                    layoutManager =
                        object : LinearLayoutManager(requireContext()) {
                            override fun canScrollVertically(): Boolean = false
                        }
                    adapter = rvAdapter
                }
            }
        }
    }
}
