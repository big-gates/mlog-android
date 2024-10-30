package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBTestApi
import com.kychan.mlog.core.testing.http.readFile
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetMoviePopularWithProviderTMDBTestApiUnitTest {

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

    @Test
    fun `watch provider가 8이면 netflix 파일 데이터를 반환해야한다`() = runTest{

        //given
        val page = 1
        val language = Language.KR
        val watchRegion = WatchRegion.KR
        val withWatchProvider = 8

        //when
        val result = retrofitTMDBTestApi.getMoviePopularWithProvider(
            page = page,
            language = language,
            watchRegion = watchRegion,
            withWatchProvider = withWatchProvider
        )

        //then
        val expected = readFile("discover_movie_netflix_${page}.json", MovieDiscoverRes::class.java)

        assertEquals(expected, result)
    }

    @Test
    fun `page 2를 요청 하면 페이지 2에 대한 데이터를 반환해야 한다`() = runTest{

        //given
        val page = 2
        val language = Language.KR
        val watchRegion = WatchRegion.KR
        val withWatchProvider = 8

        //when
        val result = retrofitTMDBTestApi.getMoviePopularWithProvider(
            page = page,
            language = language,
            watchRegion = watchRegion,
            withWatchProvider = withWatchProvider
        )

        //then
        val expected = 2
        assertEquals(expected, result.page)
    }
}