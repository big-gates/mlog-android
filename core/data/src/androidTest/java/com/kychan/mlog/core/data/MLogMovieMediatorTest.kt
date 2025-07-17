package com.kychan.mlog.core.data

import androidx.test.core.app.ApplicationProvider
import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBTestApi
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.model.Language
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MLogMovieMediatorTest {

    private val mockDb = MlogDatabase.create(
        ApplicationProvider.getApplicationContext(),
        useInMemory = true
    )

    private val mockApi: RetrofitTMDBApi = RetrofitTMDBTestApi()

    @Test
    fun a() =  runTest {

        val movieId = 15155
        val language = Language.KR
        val appendToResponse = ""

        val a = mockApi.getMovieDetail(
            movieId, language, appendToResponse
        )

        val b = a

    }
}