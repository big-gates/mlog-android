package com.kychan.mlog.core.dataSourceRemote.http

import com.kychan.mlog.core.dataSourceRemote.http.model.MovieDiscoverRes
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `read_file`() {
        val result = readFile(this.javaClass,"discover_movie_watcha_1.json", MovieDiscoverRes::class.java)

        assertThat(result, instanceOf(MovieDiscoverRes::class.java))
    }
}