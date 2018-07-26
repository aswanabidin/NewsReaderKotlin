package mumtaazstudio.newsreader_kotlin.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url

object RetrofitClient {
    private var retrofitClient:Retrofit?=null

    fun getClient(baseUrl: String?):Retrofit{
        if (retrofitClient == null)
        {
            retrofitClient = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofitClient!!

    }
}