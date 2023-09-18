package com.example.shpp_task1.presentation.fragments.detailView

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.shpp_task1.R
import com.example.shpp_task1.data.model.Contact
import com.example.shpp_task1.databinding.FragmentDetailViewBinding
import com.example.shpp_task1.utils.constants.Constants
import com.example.shpp_task1.utils.constants.FeatureFlags
import com.example.shpp_task1.utils.ext.setImageByGlide
import com.example.shpp_task1.utils.ext.setImageByPicasso
import com.example.shpp_task1.utils.viewBinding

const val TRANSITION_DELAY = 100L

/**
 * Fragment class for displaying detailed information about a contact.
 */
class DetailViewFragment : Fragment(R.layout.fragment_detail_view) {

    private val binding by viewBinding<FragmentDetailViewBinding>()
    private val args: DetailViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition(TRANSITION_DELAY, java.util.concurrent.TimeUnit.MILLISECONDS)
        setContactInfo()
        setSharedElementsTransition(args.contactInfo)
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setSharedElementsTransition(contact: Contact) {
        with(binding) {
            avatar.transitionName =
                Constants.TRANSITION_NAME_AVATAR + contact.id
            userName.transitionName = Constants.TRANSITION_NAME_USERNAME + contact.id
            career.transitionName = Constants.TRANSITION_NAME_CAREER + contact.id
        }
        val animation = context?.let {
            TransitionInflater.from(it).inflateTransition(
                android.R.transition.move
            )
        }
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun setContactInfo() {
        val contact = args.contactInfo
        with(binding) {
            userName.text = contact.username
            homeAddress.text = contact.address
            career.text = contact.career
            if (FeatureFlags.USE_GLIDE) avatar.setImageByGlide(contact.avatar.toString())
            else avatar.setImageByPicasso(contact.avatar.toString())
        }
    }

    private fun setListeners() {
        setBackButtonListener()
    }

    private fun setBackButtonListener() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}