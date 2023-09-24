package com.example.shpp_task1.presentation.fragments.auth.registerExt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentRegisterExtBinding
import com.example.shpp_task1.utils.constants.FeatureFlags
import com.example.shpp_task1.utils.ext.setImageByGlide
import com.example.shpp_task1.utils.ext.setImageByPicasso
import com.example.shpp_task1.utils.viewBinding

class RegisterExtFragment : Fragment(R.layout.fragment_register_ext) {

    private val binding by viewBinding<FragmentRegisterExtBinding>()
    private var avatarUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        setAddAvatarButtonListener()
        setCancelButtonListener()
    }


    private fun setAddAvatarButtonListener() {
        binding.addAvatar.setOnClickListener {
            openGallery()
        }
    }

    private fun setCancelButtonListener(){
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val selectedImageUri = result.data?.data
            avatarUri = selectedImageUri
            with(binding) {
                if (FeatureFlags.USE_GLIDE) avatar.setImageByGlide(avatarUri.toString())
                else avatar.setImageByPicasso(avatarUri.toString())
            }
        }
    }
}