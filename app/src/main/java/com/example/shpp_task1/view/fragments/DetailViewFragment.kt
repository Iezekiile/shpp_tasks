package com.example.shpp_task1.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.shpp_task1.R
import com.example.shpp_task1.databinding.FragmentDetailViewBinding
import com.example.shpp_task1.model.Contact
import com.example.shpp_task1.utils.Constants

const val TRANSITION_DELAY = 100L

/**
 * Fragment class for displaying detailed information about a contact.
 */
class DetailViewFragment : Fragment() {
    private lateinit var binding: FragmentDetailViewBinding
    private val args: DetailViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailViewBinding.inflate(layoutInflater)
        postponeEnterTransition(TRANSITION_DELAY, java.util.concurrent.TimeUnit.MILLISECONDS)
        setContactInfo()
        setSharedElementsTransition(args.contactInfo)
        return binding.root
    }

    /**
     * Sets the transition animation for shared elements.
     */
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

    /**
     * Sets the contact information in the layout.
     */
    private fun setContactInfo() {
        val contact = args.contactInfo
        with(binding) {
            userName.text = contact.username
            homeAddress.text = contact.address
            career.text = contact.career
            Glide.with(avatar.context)
                .load(contact.avatar)
                .circleCrop()
                .placeholder(R.drawable.ic_contact_avatar)
                .error(R.drawable.ic_contact_avatar)
                .into(avatar)
        }
    }
}