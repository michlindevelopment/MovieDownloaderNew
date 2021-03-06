package com.michlindev.moviedownloader.debug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.michlindev.moviedownloader.databinding.DebugFragmentBinding


class DebugFragment : Fragment() {

    private val viewModel: DebugViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DebugFragmentBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.notification.observe(viewLifecycleOwner) {

            /*when (it){
                DebugViewModel.NotificationType.SINGLE -> {

                    lifecycleScope.launch(Dispatchers.IO) {
                        val notificationMovies = mutableListOf<Movie>()
                        val movies = MovieListRepo.getMoviesAsync(MutableLiveData("Die Hard"), null)
                        notificationMovies.add(movies[1])

                        withContext(Dispatchers.Main) {
                            Glide.with(requireContext())
                                .asBitmap()
                                .load(notificationMovies[0].large_cover_image)
                                .into(object : CustomTarget<Bitmap>() {
                                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                        Notification.showNotification(notificationMovies, resource, requireContext())
                                    }

                                    override fun onLoadCleared(placeholder: Drawable?) {
                                    }
                                })
                        }

                    }


                }
                DebugViewModel.NotificationType.MULTIPLE -> {

                    lifecycleScope.launch {
                        val notificationMovies = mutableListOf<Movie>()
                        val movies = MovieListRepo.getMoviesAsync(MutableLiveData("Die Hard"), null)
                        notificationMovies.add(movies[1])
                        notificationMovies.add(movies[2])
                        notificationMovies.add(movies[3])
                        Notification.showNotification(movies, null, requireContext())
                    }

                }

                else -> {}
            }*/

        }

        return binding.root
    }


}