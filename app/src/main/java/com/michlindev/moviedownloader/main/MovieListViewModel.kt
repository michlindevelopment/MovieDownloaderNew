package com.michlindev.moviedownloader.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michlindev.moviedownloader.SharedPreferenceHelper
import com.michlindev.moviedownloader.SingleLiveEvent
import com.michlindev.moviedownloader.data.Constants.SEARCH_PAGES
import com.michlindev.moviedownloader.data.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel : ViewModel(), ItemListener {

    var itemList = MutableLiveData<List<Movie?>>()
    var isLoading = MutableLiveData(false)
    var searchVisible = MutableLiveData(false)
    var imdbClick = SingleLiveEvent<String>()
    var notifyAdapter = SingleLiveEvent<Int>()
    var showToast = SingleLiveEvent<String>()
    var qualitySelectionDialog = SingleLiveEvent<Movie>()
    var searchField = MutableLiveData<String>()
    var maxProgressValue = MutableLiveData(SharedPreferenceHelper.pagesNumber)
    var progress = MutableLiveData(0)

    init {
        getMovies()
    }

    fun searchMovie() {
        maxProgressValue.postValue(SEARCH_PAGES)
        progress.postValue(0)
        itemList.postValue(mutableListOf<Movie>())
        viewModelScope.launch(Dispatchers.IO) {
            itemList.postValue(MovieListRepo.getMoviesAsync(searchField.value, progress, true))
        }

    }

    fun getMovies() {
        var movies = mutableListOf<Movie>()
        maxProgressValue.postValue(SharedPreferenceHelper.pagesNumber)
        searchVisible.postValue(false)
        itemList.postValue(movies)
        progress.postValue(0)
        isLoading.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {

            movies.addAll(MovieListRepo.getMoviesAsync(null, progress, false))


            withContext(Dispatchers.Main) {
                movies = MovieListRepo.applyFilters(movies)
                movies = MovieListRepo.markDownloaded(movies)

                if (movies.isNotEmpty()) {
                    val max = movies.maxOf { it.id }
                    SharedPreferenceHelper.lastMovie = max
                    itemList.postValue(movies)
                } else {
                    showToast.postValue("Error getting movies")
                }
                isLoading.postValue(false)
            }
        }

    }

    fun updateMovieDownloaded(item: Movie) {
        itemList.value?.find { it == item }?.dowloaded = true
        notifyAdapter.postValue(itemList.value?.indexOf(item))
    }

    override fun downloadClick(item: Movie) {
        qualitySelectionDialog.postValue(item)
    }


    override fun imdbLogoClick(item: Movie) {
        val itemIndex = itemList.value?.indexOf(item)
        itemIndex?.let { itemList.value?.get(it)?.progressing = true
            notifyAdapter.postValue(it)
        }

        viewModelScope.launch(Dispatchers.IO) {

            val realRating = MovieListRepo.getRealRating(item.imdb_code)
            if (realRating == null)
                showToast.postValue("Error in IMDb")

            withContext(Dispatchers.Main) {
                itemIndex?.let {
                    realRating?.let { rating ->
                        itemList.value?.get(it)?.realRating = rating
                    }
                    itemList.value?.get(it)?.progressing = false
                    notifyAdapter.postValue(it)
                }
            }
        }
    }

    override fun posterClick(item: String) {
        imdbClick.postValue(item)
    }
}