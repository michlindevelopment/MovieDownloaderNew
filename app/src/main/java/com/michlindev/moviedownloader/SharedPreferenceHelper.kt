package com.michlindev.moviedownloader

import android.content.Context
import android.content.SharedPreferences
import com.michlindev.moviedownloader.data.Constants


object SharedPreferenceHelper {
    private const val PAGES_NUMBER = "PAGES_NUMBER"
    private const val MIN_RATING = "MIN_RATING"
    private const val MIN_YEAR= "MIN_YEAR"
    private const val ENGLISH_ONLY= "ENGLISH_ONLY"
    private const val GENRES= "GENRES"
    private const val APP_SHARED_PREFS = "APP_SHARED_PREFS"
    private const val USER_UID = "USER_UID"
    private const val LAST_MOVIE = "LAST_MOVIE"
    private const val UPLOAD_REQUIRED = "UPLOAD_REQUIRED"

    private var preferences:SharedPreferences = MovieDownloader.appContext.getSharedPreferences(APP_SHARED_PREFS,Context.MODE_PRIVATE)

    var pagesNumber: Int
        get() = preferences.getInt(PAGES_NUMBER, Constants.PAGES)
        set(value) = preferences.edit().putInt(PAGES_NUMBER, value).apply()

    var minRating: Int
        get() = preferences.getInt(MIN_RATING, Constants.MIN_RATING)
        set(value) = preferences.edit().putInt(MIN_RATING, value).apply()

    var minYear: Int
        get() = preferences.getInt(MIN_YEAR, Constants.MIN_YEAR)
        set(value) = preferences.edit().putInt(MIN_YEAR, value).apply()

    var englishOnly: Boolean
        get() = preferences.getBoolean(ENGLISH_ONLY, false)
        set(value) = preferences.edit().putBoolean(ENGLISH_ONLY, value).apply()

    var lastMovie: Long
        get() = preferences.getLong(LAST_MOVIE, -1)
        set(value) = preferences.edit().putLong(LAST_MOVIE, value).apply()

    var uploadRequired: Boolean
        get() = preferences.getBoolean(UPLOAD_REQUIRED, false)
        set(value) = preferences.edit().putBoolean(UPLOAD_REQUIRED, value).apply()




    var genres: MutableSet<String>
        get() {
            val genres = preferences.getStringSet(GENRES, null)
            return if (genres==null) {
                val genresSet = mutableSetOf<String>()
                genresSet.addAll(MovieDownloader.appContext.resources.getStringArray(R.array.genres))
                genresSet
            } else
                genres
        }
        set(value) = preferences.edit().putStringSet(GENRES, value).apply()

    var uid: String?
        get() = preferences.getString(USER_UID, null)
        set(value) = preferences.edit().putString(USER_UID, value).apply()
}