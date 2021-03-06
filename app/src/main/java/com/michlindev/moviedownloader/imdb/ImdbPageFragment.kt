package com.michlindev.moviedownloader.imdb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.michlindev.moviedownloader.data.Constants.IMDB_CODE
import com.michlindev.moviedownloader.databinding.FragmentImdbPageBinding

class ImdbPageFragment : Fragment() {

    private val viewModel: ImdbPageViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = FragmentImdbPageBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.getImdb(arguments?.getString(IMDB_CODE))
        viewModel.imdbClick.observe(viewLifecycleOwner){
            startActivity(it)
        }

        return binding.root
    }

}