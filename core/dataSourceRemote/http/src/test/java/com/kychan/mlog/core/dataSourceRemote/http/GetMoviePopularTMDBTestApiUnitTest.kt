package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.model.MoviePopularRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetMoviePopularTMDBTestApiUnitTest {

    val retrofitTMDBTestApi = RetrofitTMDBTestApi()

    @Test
    fun `mlog 파일 데이터를 반환해야한다`() = runTest{

        //given
        val page = 1
        val language = Language.KR
        val watchRegion = WatchRegion.KR

        //when
        val result = retrofitTMDBTestApi.getMoviePopular(
            page = page,
            language = language,
            watchRegion = watchRegion,
        )

        //then
        val expected = readFile("discover_movie_mlog_${page}.json", MoviePopularRes::class.java)

        assertEquals(expected, result)
    }

    @Test
    fun `page 2를 요청 하면 페이지 2에 대한 데이터를 반환해야 한다`() = runTest{

        //given
        val page = 2
        val language = Language.KR
        val watchRegion = WatchRegion.KR

        //when
        val result = retrofitTMDBTestApi.getMoviePopular(
            page = page,
            language = language,
            watchRegion = watchRegion,
        )

        //then
        val expected = 2
        assertEquals(expected, result.page)
    }
}