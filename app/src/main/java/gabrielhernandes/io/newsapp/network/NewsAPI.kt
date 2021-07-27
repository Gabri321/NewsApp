package gabrielhernandes.io.newsapp.network

import gabrielhernandes.io.newsapp.model.NewsResponse
import gabrielhernandes.io.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String = "br",
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY

    ): Response<NewsResponse>

    @GET("/v2/top-headlines")
    suspend fun searchNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}