package com.orlinskas.cocktail.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.orlinskas.cocktail.R
import com.orlinskas.cocktail.databinding.DialogPhotoBinding

class PhotoDialogFragment : FullScreenDialog() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: DialogPhotoBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_photo, container, false)

        val url = requireArguments().getString(KEY_URL)!!

        Glide.with(requireContext())
            .load(url)
            .into(binding.photoView)

        binding.photoView.setOnOutsidePhotoTapListener {
            dismissAllowingStateLoss()
        }

        binding.buttonClose.setOnClickListener {
            dismissAllowingStateLoss()
        }

        return binding.root
    }

    companion object {
        private const val KEY_URL = "URL"

        fun open(fragmentManager: FragmentManager, url: String) {
            val dialog = PhotoDialogFragment()
            val args = Bundle()
            args.putString(KEY_URL, url)
            dialog.arguments = args
            dialog.show(fragmentManager, PhotoDialogFragment::class.java.simpleName)
        }
    }
}
