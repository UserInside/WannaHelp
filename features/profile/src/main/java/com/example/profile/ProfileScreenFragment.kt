package com.example.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.FragmentNavigationListener
import com.example.data.di.DataModule
import com.example.profile.databinding.FragmentProfileScreenBinding
import com.example.profile.di.DaggerProfileComponent
import com.example.profile.di.ProfileComponent
import com.example.profile.di.ProfileModule
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.common.R as commonR

class ProfileScreenFragment : Fragment() {
    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var navListener: FragmentNavigationListener

    private val profileComponent: ProfileComponent by lazy {
        DaggerProfileComponent.builder()
            .profileModule(ProfileModule())
            .dataModule(DataModule(requireContext()))
            .build()
    }

    @Inject
    lateinit var vmFactory: ProfileViewModelFactory

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
        profileComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[ProfileViewModel::class]

        val customToolbar = binding.appBar.profileToolbar
        customToolbar.apply {
            title = getString(commonR.string.profile)
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
                                navListener = requireActivity() as FragmentNavigationListener
                                navListener.navigateTo("profileEditing")
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
