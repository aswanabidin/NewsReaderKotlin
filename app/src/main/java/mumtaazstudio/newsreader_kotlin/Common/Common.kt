package mumtaazstudio.newsreader_kotlin.Common

import mumtaazstudio.newsreader_kotlin.Interface.NewsServices
import mumtaazstudio.newsreader_kotlin.Remote.RetrofitClient

object Common{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "548e86a3c94d49b584f9c06f6b53a1ab"

    val newsService:NewsServices
    get() = RetrofitClient.getClient(BASE_URL).create(NewsServices::class.java )

    fun getNewsAPI(source:String) : String {
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
                .append(source)
                .append("&apiKey=")
                .append(API_KEY)
                .toString()
        return apiUrl
    }
}