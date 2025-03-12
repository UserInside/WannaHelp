package com.example.wannahelp.profileEditScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import com.example.wannahelp.databinding.FragmentChangeAvatarDialogBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

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

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    saveAvatarToInternalStorage(
                        context = requireContext(),
                        imageUri = avatarFile.toUri(),
                    )
                    val bundle = Bundle()
                    bundle.putString("photoPath", avatarFile.path)
                    parentFragmentManager.setFragmentResult("photoPath", bundle)
                    dismiss()
                }
            }

        takePhotoFromGalleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageURI = result.data?.data
                    imageURI?.let {
                        saveAvatarToInternalStorage(
                            context = requireContext(),
                            imageUri = imageURI,
                        )
                    }
                    val bundle = Bundle().apply {
                        putString("choosePhoto", imageURI.toString())
                    }
                    parentFragmentManager.setFragmentResult("choosePhoto", bundle)
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
                    putString("deletePhoto", null)
                }
            parentFragmentManager.setFragmentResult("deletePhoto", bundle)
            dismiss()
        }
    }

    companion object {
        fun newInstance() = ChangeAvatarDialogFragment()
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
            Log.e("AVATAR", "1")

            val photoFile: File? =
                try {
                    createAvatarFile()

                } catch (ex: IOException) {
                    Toast.makeText(requireContext(), "Ошибка создания файла", Toast.LENGTH_SHORT)
                        .show()
                    null
                }
            photoFile?.let { file ->
                Log.e("AVATAR", "2")

                val photoURI: Uri =
                    FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
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
            "avatar",
            ".jpg",
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
                    Toast.makeText(requireContext(), "Ошибка создания файла", Toast.LENGTH_SHORT)
                        .show()
                    null
                }
            avatarFile?.let { file ->
                val avatarURI: Uri =
                    FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
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
        val inputStream: InputStream =
            context.contentResolver.openInputStream(imageUri) ?: return null

        val file = File(context.filesDir, "avatar.jpg")
//todo refactor try-w-res
        try {
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
