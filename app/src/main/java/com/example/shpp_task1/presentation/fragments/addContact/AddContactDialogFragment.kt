package com.example.shpp_task1.presentation.fragments.addContact


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.shpp_task1.R
import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.databinding.FragmentAddContactBinding
import com.example.shpp_task1.presentation.fragments.contacts.vm.ContactsViewModel
import com.example.shpp_task1.utils.constants.FeatureFlags
import com.example.shpp_task1.utils.ext.setImageByGlide
import com.example.shpp_task1.utils.ext.setImageByPicasso
import com.example.shpp_task1.utils.viewBinding


/**
 * DialogFragment class for adding a new contact using a dialog.
 *
 * @param contactsViewModel The ViewModel responsible for managing contact data.
 */
class AddContactDialogFragment(private val contactsViewModel: ContactsViewModel) :
    DialogFragment(R.layout.fragment_add_contact) {

    private val addContactBinding by viewBinding<FragmentAddContactBinding>()
    private var avatarUri: Uri? = null


    /**
     * Called immediately after onCreateView() has returned, but before any saved state has been restored.
     *
     * @param view The view returned by onCreateView().
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    /**
     * Sets listeners for UI elements.
     */
    private fun setListeners() {
        setSaveButtonListener()
        setAddAvatarButtonListener()
        setBackButtonListener()
    }

    private fun setBackButtonListener() {
        addContactBinding.buttonBack.setOnClickListener {
            dismiss()
        }
    }

    /**
     * Sets a click listener for the "Save" button.
     * Creates a new contact object and adds it to the ViewModel on button click.
     */
    private fun setSaveButtonListener() {
        addContactBinding.saveButton.setOnClickListener {
            val contact = Contact(
                // TODO: Make a normal assignment of id's
                0, avatarUri.toString(),
                addContactBinding.usernameTextInput.text.toString(),
                addContactBinding.careerTextInput.text.toString(),
                addContactBinding.emailTextInput.text.toString(),
                addContactBinding.phoneTextInput.text.toString(),
                addContactBinding.addressTextLayout.text.toString(),
                addContactBinding.dateOfBirthTextInput.text.toString()
            )

            contactsViewModel.onContactAdd(contact)
            dismiss()
        }
    }

    /**
     * Sets a click listener for the "Add Avatar" button.
     * Opens the gallery to select an image.
     */
    private fun setAddAvatarButtonListener() {
        addContactBinding.addAvatar.setOnClickListener {
            openGallery()
        }
    }

    /**
     * Opens the gallery to select an image.
     */
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    /**
     * ActivityResultLauncher for handling the result of the gallery intent.
     */
    private val galleryLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val selectedImageUri = result.data?.data
            avatarUri = selectedImageUri
            with(addContactBinding) {
                if (FeatureFlags.USE_GLIDE) avatar.setImageByGlide(avatarUri.toString())
                else avatar.setImageByPicasso(avatarUri.toString())
            }
        }
    }
}
