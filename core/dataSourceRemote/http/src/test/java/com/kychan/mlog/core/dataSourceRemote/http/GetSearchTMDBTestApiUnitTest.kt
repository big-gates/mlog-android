package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.testing.http.RetrofitTMDBTestApi
import com.kychan.mlog.core.testing.http.readFile
import com.kychan.mlog.core.dataSourceRemote.http.model.MovieSearchRes
import com.kychan.mlog.core.model.Language
import com.kychan.mlog.core.model.WatchRegion
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetSearchTMDBTestApiUnitTest {

    val retrofitTMDBTestApi = RetrofitTMDBTestApi()

    @Test
    fun `query가 어벤져로 검색했을 때 나오는 데이터를 반환해야한다`() = runTest{

        //given
        val page = 1
        val language = Language.KR
        val region = WatchRegion.KR
        val query = "어벤져"

        //when
        val result = retrofitTMDBTestApi.getSearch(
            page = page,
            language = language,
            watchRegion = region,
            query = query
        )

        //then
        val expected = readFile("search_movie_avg_${page}_${language}_${region}.json", MovieSearchRes::class.java)

        assertEquals(expected, result)
    }

    @Test
    fun `검색 데이터가 없을 때 빈 데이터가 나와야 한다`() = runTest{

        //given
        val page = 1
        val language = Language.KR
        val region = WatchRegion.KR
        val query = "" // 결과가 없는 쿼리 요청 테스트 따로

        //when
        val result = retrofitTMDBTestApi.getSearch(
            page = page,
            language = language,
            watchRegion = region,
            query = query
        )

        //then
        val expected = readFile("search_movie_${page}_${language}_${region}_empty.json", MovieSearchRes::class.java)

        assertEquals(expected.results, result.results)
    }

    @Test
    fun `page 2를 요청 하면 페이지 2에 대한 데이터를 반환해야 한다`() = runTest{

        //given
        val page = 2
        val language = Language.KR
        val region = WatchRegion.KR
        val query = "어벤져"

        //when
        val result = retrofitTMDBTestApi.getSearch(
            page = page,
            language = language,
            watchRegion = region,
            query = query
        )

        //then
        val expected = 2
        assertEquals(expected, result.page)
    }
}