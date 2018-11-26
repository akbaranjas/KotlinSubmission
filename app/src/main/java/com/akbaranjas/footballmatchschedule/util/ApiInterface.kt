package com.akbaranjas.footballmatchschedule.util

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.models.*
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

interface ApiInterface {
    @GET("/api/v1/json/{key}/eventspastleague.php")
    fun getMatchList(@Path("key") id: String, @Query("id") leagueId: String): Single<MatchResponse>

    @GET("/api/v1/json/{key}/eventsnextleague.php")
    fun getNextMatchList(@Path("key") id: String, @Query("id") leagueId: String): Single<MatchResponse>

    @GET("/api/v1/json/{key}/lookupteam.php")
    fun getTeam(@Path("key") id: String, @Query("id") teamId: String): Single<TeamResponse>

    @GET("/api/v1/json/{key}/lookupevent.php")
    fun getDetailMatch(@Path("key") id: String, @Query("id") leagueId: String): Single<MatchResponse>

    @GET("/api/v1/json/{key}/searchevents.php")
    fun searchEvent(@Path("key") id: String, @Query("e") search: String): Observable<SearchResponse>

    @GET("/api/v1/json/{key}/search_all_teams.php")
    fun getTeamList(@Path("key") id: String, @Query("l") league: String): Single<TeamResponse>

    @GET("/api/v1/json/{key}/searchteams.php")
    fun searchTeam(@Path("key") id: String, @Query("t") team: String): Observable<TeamResponse>

    @GET("/api/v1/json/{key}/lookup_all_players.php")
    fun getPlayerbyTeam(@Path("key") id: String, @Query("id") team: String): Single<PlayerResponse>

    @GET("/api/v1/json/{key}/lookupplayer.php")
    fun getPlayerDetail(@Path("key") id: String, @Query("id") team: String): Single<PlayerDetailResponse>

    companion object Factory {

        fun create(): ApiInterface {
            val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val client : OkHttpClient = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
            }.build()

            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}