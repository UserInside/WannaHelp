package com.example.wannahelp.presentation.profileEditScreen

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.navigation.fragment.NavHostFragment
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentEditProfileScreenBinding
import com.example.wannahelp.presentation.ToolbarFragment
import java.io.File

class EditProfileScreenFragment : ToolbarFragment(R.layout.fragment_edit_profile_screen) {
    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.apply {
            title = getString(R.string.tv_title_edit_profile)
            setNavigationOnClickListener {
                NavHostFragment.findNavController(this@EditProfileScreenFragment)
                    .popBackStack()
            }
        }
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_check_24)
            setOnClickListener {
                NavHostFragment.findNavController(this@EditProfileScreenFragment)
                    .navigate(R.id.navigateToProfileScreen)
            }
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentEditProfileScreenBinding.bind(content ?: view)

        val avatarFile = File(requireContext().filesDir, getString(R.string.file_name_avatar))
        if (avatarFile.exists()) {
            binding.imgAvatar.setImageURI(avatarFile.toUri())
        } else {
            binding.imgAvatar.setImageResource(R.drawable.avatar_placeholder)
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
