package com.example.wannahelp.profileEditScreen

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.wannahelp.MainActivity
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentEditProfileScreenBinding
import com.example.wannahelp.newsScreen.newsFilterScreen.NewsFilterFragment
import java.io.File

class EditProfileScreenFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val avatarFile = File(requireContext().filesDir, getString(R.string.file_name_avatar))
        if (avatarFile.exists()) {
            binding.imgAvatar.setImageURI(avatarFile.toUri())
        } else {
            binding.imgAvatar.setImageResource(R.drawable.avatar_placeholder)
        }

        requireActivity().apply {
            title = getString(R.string.tv_title_edit_profile)
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            addMenuProvider(
                object : MenuProvider {
                    override fun onCreateMenu(
                        menu: Menu,
                        menuInflater: MenuInflater,
                    ) {
                        menuInflater.inflate(R.menu.menu_toolbar_edit_profile, menu)
                    }

                    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                        return when (menuItem.itemId) {
                            R.id.action_confirm -> {
                                NavHostFragment.findNavController(this@EditProfileScreenFragment)
                                    .popBackStack() // add save fields values
                                true
                            }

                            android.R.id.home -> {
                                NavHostFragment.findNavController(this@EditProfileScreenFragment)
                                    .popBackStack()
                                true
                            }

                            else -> false
                        }
                    }
                },
                viewLifecycleOwner,
                Lifecycle.State.RESUMED,
            )
        }

        binding.btnChangePhoto.setOnClickListener {
            showChangePhotoDialog()
        }

        parentFragmentManager.setFragmentResultListener(
            CHOOSE_AVATAR_KEY,
            viewLifecycleOwner,
        ) { _, bundle ->
            val photoPath = bundle.getString(CHOOSE_AVATAR_KEY)
            photoPath?.let {
                binding.imgAvatar.setImageURI(photoPath.toUri())
            }
        }

        parentFragmentManager.setFragmentResultListener(
            PHOTO_PATH_KEY,
            viewLifecycleOwner,
        ) { _, bundle ->
            val photoPath = bundle.getString(PHOTO_PATH_KEY)
            photoPath?.let {
                val bitmap = BitmapFactory.decodeFile(it)
                binding.imgAvatar.setImageBitmap(bitmap)
            }
        }

        parentFragmentManager.setFragmentResultListener(
            DELETE_AVATAR_KEY,
            viewLifecycleOwner,
        ) { _, _ ->
            binding.imgAvatar.setImageResource(R.drawable.avatar_placeholder)
            avatarFile.delete()
        }
    }

    private fun showChangePhotoDialog() {
        val dialog = ChangeAvatarDialogFragment.newInstance()
        dialog.show(parentFragmentManager, CHANGE_AVATAR_DIALOG)
    }

    private companion object {
        const val CHANGE_AVATAR_DIALOG = "ChangeAvatarDialogFragment"
    }
}
