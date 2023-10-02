package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ReadFileUnitTest {
    @Test
    fun read_file() {
        val result = readFile("discover_movie_watcha_1.json", MovieDiscoverRes::class.java)

        assertThat(result, instanceOf(MovieDiscoverRes::class.java))
    }
}