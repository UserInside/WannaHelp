package com.example.wannahelp.profileEditScreen

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentEditProfileScreenBinding

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

        binding

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_confirm -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.navigateToProfileScreen)
                    true
                }

                else -> false
            }
        }

        binding.profileEditLayout.btnChangePhoto.setOnClickListener {
            showChangePhotoDialog()
        }

        parentFragmentManager.setFragmentResultListener(
            "photoPath",
            viewLifecycleOwner,
        ) { _, bundle ->
            val photoPath = bundle.getString("photoPath")
            photoPath?.let {
                val bitmap = BitmapFactory.decodeFile(it)
                binding.profileEditLayout.imgAvatar.setImageBitmap(bitmap)
            }
        }

        parentFragmentManager.setFragmentResultListener(
            "deletePhoto",
            viewLifecycleOwner,
        ) { _, _ ->
            binding.profileEditLayout.imgAvatar.setImageResource(R.drawable.avatar_placeholder)
        }
    }

    private fun showChangePhotoDialog() {
        val dialog = ChangeAvatarDialogFragment.newInstance()
        dialog.show(parentFragmentManager, "ChangeAvatarDialogFragment")
    }
}
