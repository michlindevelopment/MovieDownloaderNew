package com.michlindev.moviedownloader.data

import com.google.gson.annotations.SerializedName
import com.michlindev.moviedownloader.main.ListAdapterItem
import java.io.Serializable
import java.util.*

data class Movie(
    override val id: Long = 0,
    @SerializedName("title_english") var title_english: String,
    @SerializedName("title") var title: String,
    @SerializedName("url") var url: String,
    @SerializedName("torrents") var torrents: List<Torrents>,
    @SerializedName("small_cover_image") var small_cover_image: String,
    @SerializedName("medium_cover_image") var medium_cover_image: String,
    @SerializedName("large_cover_image") var large_cover_image: String,
    @SerializedName("imdb_code") var imdb_code: String,
    @SerializedName("year") private var tempYear: Int?,
    @SerializedName("rating") var rating: Double,
    @SerializedName("genres") private var tempGenres: List<String>?,
    @SerializedName("summary") var summary: String,
    @SerializedName("background_image") var background_image: String,
    @SerializedName("date_uploaded_unix") var date_uploaded_unix: Long,
    @SerializedName("runtime") var runtime: Int,
    @SerializedName("yt_trailer_code") var ytsTrailer: String,
    @SerializedName("description_full") var description_full: String,
    @SerializedName("synopsis") var synopsis: String,
    @SerializedName("language") var language: String
) : ListAdapterItem, Serializable {

    val fullLanguage: String
        get() = Locale(language).displayLanguage

    val genres: List<String>
        get() = tempGenres ?: listOf()

    val ratingString: String
        get() = rating.toString()

    //TODO Load other if large fails
    val coverImages: List<String>
        get() = listOf(large_cover_image, medium_cover_image, small_cover_image)

    val year: Int
        get() {
            return if (tempYear == null) 0 else tempYear as Int
        }


    var progressing: Boolean = false
    var dowloaded: Boolean = false
    var realRating: String? = null



}




