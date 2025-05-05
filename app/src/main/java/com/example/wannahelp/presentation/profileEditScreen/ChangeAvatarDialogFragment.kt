package com.example.wannahelp.presentation.profileEditScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import com.example.wannahelp.R
import com.example.wannahelp.databinding.FragmentChangeAvatarDialogBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

const val DELETE_AVATAR_KEY = "deletePhoto"
const val CHOOSE_AVATAR_KEY = "choosePhoto"
const val PHOTO_PATH_KEY = "photoPath"

class ChangeAvatarDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentChangeAvatarDialogBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var takePhotoFromGalleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var avatarFile: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChangeAvatarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    saveAvatarToInternalStorage(
                        context = context,
                        imageUri = avatarFile.toUri(),
                    )
                    val bundle = Bundle()
                    bundle.putString(PHOTO_PATH_KEY, avatarFile.path)
                    parentFragmentManager.setFragmentResult(PHOTO_PATH_KEY, bundle)
                    dismiss()
                }
            }

        takePhotoFromGalleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageURI = result.data?.data
                    imageURI?.let {
                        saveAvatarToInternalStorage(
                            context = context,
                            imageUri = imageURI,
                        )
                    }
                    val bundle =
                        Bundle().apply {
                            putString(CHOOSE_AVATAR_KEY, imageURI.toString())
                        }
                    parentFragmentManager.setFragmentResult(CHOOSE_AVATAR_KEY, bundle)
                    dismiss()
                }
            }

        binding.tvChoosePhoto.setOnClickListener {
            takePhotoFromGallery()
        }

        binding.tvMakePhoto.setOnClickListener {
            if (checkCameraPermission()) {
                makeAvatarPhoto()
            }
        }

        binding.tvDelete.setOnClickListener {
            val bundle =
                Bundle().apply {
                    putString(DELETE_AVATAR_KEY, null)
                }
            parentFragmentManager.setFragmentResult(DELETE_AVATAR_KEY, bundle)
            dismiss()
        }
    }

    private fun checkCameraPermission(): Boolean =
        (
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA,
            )
                != PackageManager.PERMISSION_GRANTED
        )

    private fun makeAvatarPhoto() {
        val makeAvatarIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (makeAvatarIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? =
                try {
                    createAvatarFile()
                } catch (ex: IOException) {
                    Toast.makeText(
                        context,
                        getString(R.string.error_file_not_created),
                        Toast.LENGTH_SHORT,
                    ).show()
                    null
                }
            photoFile?.let { file ->
                val photoURI: Uri =
                    FileProvider.getUriForFile(
                        requireContext(),
                        "${context?.packageName}.fileprovider",
                        file,
                    )
                makeAvatarIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                cameraLauncher.launch(makeAvatarIntent)
            }
        }
    }

    private fun createAvatarFile(): File {
        val storageDir = requireActivity().filesDir
        return File.createTempFile(
            AVATAR_FILE_PREFIX,
            AVATAR_FILE_SUFFIX,
            storageDir,
        ).apply {
            avatarFile = this
        }
    }

    private fun takePhotoFromGallery() {
        val takeFromGalleryIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        if (takeFromGalleryIntent.resolveActivity(requireActivity().packageManager) != null) {
            val avatarFile: File? =
                try {
                    createAvatarFile()
                } catch (ex: IOException) {
                    Toast.makeText(
                        context,
                        getString(R.string.error_file_not_created),
                        Toast.LENGTH_SHORT,
                    ).show()
                    null
                }
            avatarFile?.let { file ->
                val avatarURI: Uri =
                    FileProvider.getUriForFile(
                        requireContext(),
                        "${context?.packageName}.fileprovider",
                        file,
                    )
                takeFromGalleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, avatarURI)
                takePhotoFromGalleryLauncher.launch(takeFromGalleryIntent)
            }
        }
    }

    private fun saveAvatarToInternalStorage(
        context: Context,
        imageUri: Uri,
    ): File? {
        val file = File(context.filesDir, getString(R.string.file_name_avatar))
        return try {
            context.contentResolver.openInputStream(imageUri).use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val AVATAR_FILE_PREFIX = "avatar"
        private const val AVATAR_FILE_SUFFIX = ".jpg"

        fun newInstance() = ChangeAvatarDialogFragment()
    }
}
