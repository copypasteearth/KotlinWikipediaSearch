package jacs.apps.kotlinwiki.network

import jacs.apps.kotlinwiki.datamodel.WikiResponse
import jacs.apps.kotlinwiki.datamodel.PageData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WikiAPI {
    //https://en.wikipedia.org/w/api.php?action=query&formatversion=2&prop=extracts%7Cpageimages%7Cpageterms&gpssearch=S&redirects=&exintro=true&exsentences=2&explaintext=true&piprop=thumbnail&pithumbsize=300&rvprop=timestamp&format=json&generator=prefixsearch

    @Headers(
        "Content-Type: application/json; charset=utf-8"
    )
    @GET("api.php?")
    suspend fun getSearchRespone(
        @Query("gpssearch") searchText: String,
        @Query("action") query: String,
        @Query("formatversion") formatversion: String,
        @Query("prop") prop: String,
        @Query("exsentences") exsentences: String,
        @Query("explaintext") explaintext: String,
        @Query("piprop") piprop: String,
        @Query("format") format: String,
        @Query("generator") generator: String,
        @Query("pithumbsize") pithumbsize: String,
        @Query("exintro") exintro: String
    ): WikiResponse

    @GET("api.php?")
    suspend fun getPageData(
        @Query("action") action: String,
        @Query("page") page: String,
        @Query("prop") prop: String,
        @Query("format") format: String,
        @Query("formatversion") formatversion: String
    ): PageData
}