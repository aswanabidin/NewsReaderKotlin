package mumtaazstudio.newsreader_kotlin.Interface

import mumtaazstudio.newsreader_kotlin.Model.News
import mumtaazstudio.newsreader_kotlin.Model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsServices {

    @get:GET("https://newsapi.org/v2/sources?apiKey=548e86a3c94d49b584f9c06f6b53a1ab")

    val sources: Call<Website>

    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}