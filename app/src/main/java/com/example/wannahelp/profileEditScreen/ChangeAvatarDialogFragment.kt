package com.example.wannahelp.profileEditScreen

import android.Manifest
import android.app.Activity
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
import androidx.fragment.app.DialogFragment
import com.example.wannahelp.databinding.FragmentChangeAvatarDialogBinding
import java.io.File
import java.io.IOException

class ChangeAvatarDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentChangeAvatarDialogBinding
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var photoImagePath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeAvatarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bundle = Bundle()
                    bundle.putString("photoPath", photoImagePath)
                    parentFragmentManager.setFragmentResult("makePhoto", bundle)
                }
            }

        binding.tvMakePhoto.setOnClickListener {
            if (checkCameraPermission()) {
                createMakePhotoIntent()
                dismiss()
            }
        }

        binding.tvDelete.setOnClickListener {
            val bundle = Bundle().apply {
                putString("deletePhoto", null)
            }
            parentFragmentManager.setFragmentResult("deletePhoto", bundle)
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChangeAvatarDialogFragment()
    }

    private fun checkCameraPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        )
                != PackageManager.PERMISSION_GRANTED)
    }

    private fun createMakePhotoIntent() {
        val makePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (makePhotoIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createPhotoTempFile()
            } catch (ex: IOException) {
                Toast.makeText(requireContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show()
                null
            }
            photoFile?.let {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.fileprovider",
                    it
                )
                makePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                cameraLauncher.launch(makePhotoIntent)
            }
        }
    }

    private fun createPhotoTempFile(): File {
        val storageDir = requireContext().cacheDir
        val file = File.createTempFile("tmpPhoto", ".jpg", storageDir)
        photoImagePath = file.absolutePath
        return file
    }
}