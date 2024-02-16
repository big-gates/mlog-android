package com.kychan.mlog.core.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kychan.mlog.core.dataSourceRemote.http.api.RetrofitTMDBApi
import com.kychan.mlog.core.dataSourceRemote.http.fake.RetrofitTMDBTestApi
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Test {

    private val mockApi: RetrofitTMDBApi = RetrofitTMDBTestApi()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        println("a")
    }
}