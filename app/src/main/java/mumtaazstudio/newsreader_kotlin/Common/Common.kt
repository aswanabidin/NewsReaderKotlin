package mumtaazstudio.newsreader_kotlin.Common

import mumtaazstudio.newsreader_kotlin.Interface.NewsServices
import mumtaazstudio.newsreader_kotlin.Remote.RetrofitClient

object Common{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "548e86a3c94d49b584f9c06f6b53a1ab"

    val newsService:NewsServices
    get() = RetrofitClient.getClient(BASE_URL).create(NewsServices::class.java )
}