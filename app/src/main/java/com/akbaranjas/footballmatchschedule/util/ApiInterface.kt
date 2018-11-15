package com.akbaranjas.footballmatchschedule.util

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.models.MatchResponse
import com.akbaranjas.footballmatchschedule.models.TeamResponse
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import io.reactivex.Single
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

    companion object Factory {

        fun create(): ApiInterface {
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}