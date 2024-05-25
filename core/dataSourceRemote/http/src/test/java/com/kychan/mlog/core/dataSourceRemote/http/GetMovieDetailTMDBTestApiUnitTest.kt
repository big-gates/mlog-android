package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.core.testing.http.RetrofitTMDBTestApi
import com.kychan.mlog.core.testing.http.RetrofitTMDBTestApi
import com.kychan.mlog.core.model.Language
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetMovieDetailTMDBTestApiUnitTest {

    val retrofitTMDBTestApi = RetrofitTMDBTestApi()

    @Test
    fun `movie_id 15155에 대한 결과값이 반환되어야 한다`() = runTest{

        //given
        val movieId = 15155
        val language = Language.KR
        val appendToResponse = ""

        //when
        val result = retrofitTMDBTestApi.getMovieDetail(
            movieId = movieId,
            language = language,
            appendToResponse = appendToResponse,
        )

        //then
        val expected = 15155
        assertEquals(expected, result.id)
    }

    @Test
    fun `movie_id 15156에 대한 결과값이 반환되어야 한다`() = runTest{

        //given
        val movieId = 15156
        val language = Language.KR
        val appendToResponse = ""

        //when
        val result = retrofitTMDBTestApi.getMovieDetail(
            movieId = movieId,
            language = language,
            appendToResponse = appendToResponse,
        )

        //then
        val expected = 15156
        assertEquals(expected, result.id)
    }


}