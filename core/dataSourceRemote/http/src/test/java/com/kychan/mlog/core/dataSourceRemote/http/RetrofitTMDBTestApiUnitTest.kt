package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class RetrofitTMDBTestApiUnitTest {

    val retrofitTMDBTestApi = RetrofitTMDBTestApi()

    @Test
    fun `watch provider가 97이면 watcha 파일 데이터를 반환해야한다`() = runTest{

        //given
        val page = 1
        val language = Language.KR
        val watchRegion = WatchRegion.KR
        val withWatchProvider = 97

        //when
        val result = retrofitTMDBTestApi.getMoviePopularWithProvider(
            page = page,
            language = language,
            watchRegion = watchRegion,
            withWatchProvider = withWatchProvider
        )

        //then
        val expected = readFile("discover_movie_watcha_${page}.json", MovieDiscoverRes::class.java)

        assertEquals(expected, result)
    }
}