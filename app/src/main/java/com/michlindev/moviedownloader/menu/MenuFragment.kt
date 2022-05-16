package com.michlindev.moviedownloader.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.michlindev.moviedownloader.DLog
import com.michlindev.moviedownloader.R
import com.michlindev.moviedownloader.SharedPreferenceHelper
import com.michlindev.moviedownloader.databinding.FragmentMenuBinding
import com.michlindev.moviedownloader.databinding.SplashFragmentBinding
import com.michlindev.moviedownloader.splash.SplashViewModel

class MenuFragment : Fragment() {

    private val viewModel: MenuViewModel by activityViewModels()

    //TODO Set default values
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = FragmentMenuBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.pageNumbersPosition.observe(viewLifecycleOwner) {
            SharedPreferenceHelper.pagesNumber = it?.let { it1 -> viewModel.pageNumbersArray.value?.get(it1) } ?: 10
        }

        viewModel.ratingPosition.observe(viewLifecycleOwner) {
            SharedPreferenceHelper.minRating = it?.let { it1 -> viewModel.ratingArray.value?.get(it1) } ?: 0
        }

        viewModel.yearPosition.observe(viewLifecycleOwner) {
            SharedPreferenceHelper.minYear = it?.let { it1 -> viewModel.yearArray.value?.get(it1) } ?: 2000
        }

        return binding.root
    }

}