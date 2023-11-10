package com.kychan.mlog.core.dataSourceLocal.room.movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.common.extenstions.toDateTimeFormat
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.SearchDao
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.model.RecentSearchEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SearchDaoTest {

    private lateinit var searchDao: SearchDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() = runTest{
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MlogDatabase::class.java,
        ).build()
        searchDao = db.searchDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * unique constraint 검증 테스트
     *
     * 데이터베이스에 다른 id를 가지면서 동일한 text가 존재 할 경우
     *
     * 데이터베이스에 저장 돼서는 안된다.
     *
     * SQLiteConstraintException을 발생시켜야 한다.
     */
    @Test
    fun if_the_id_is_not_same_and_the_text_is_same_it_is_must_be_not_stored_in_the_database() = runTest {
        //given
        val data = RecentSearchEntity(
            text = "hi",
            createdAt = System.currentTimeMillis().toDateFormat(),
        )
        searchDao.upsertRecentSearch(data)

        //when
        val exception = runCatching {
            searchDao.upsertRecentSearch(data.copy(id = data.id ?: (1 + 1)))
        }.exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * update 테스트
     *
     * 데이터베이스에 동일한 text, id가 존재 하면 update를 해야한다.
     */
    @Test
    fun if_the_id_is_same_and_the_text_is_same_it_is_must_be_updated_in_the_database() = runTest {
        //given
        val data = RecentSearchEntity(
            id = 1,
            text = "hi",
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        searchDao.upsertRecentSearch(data)

        val updatedData = data.copy(
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        //when
        searchDao.upsertRecentSearch(updatedData)

        //then
        val result = searchDao.getRecentSearch(updatedData.text).first()
        assertTrue(result == updatedData)
    }

    /**
     * delete all 테스트
     *
     * 데이터베이스에 모든 RecentSearchEntity 를 삭제 해야한다.
     */
    @Test
    fun recentSearchEntity_is_must_be_deleted_all_in_the_database() = runTest {
        //given
        val data = RecentSearchEntity(
            id = 1,
            text = "hi",
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        searchDao.upsertRecentSearch(data)
        searchDao.upsertRecentSearch(data.copy(
            id = data.id?.plus(1),
            text = "asd"
        ))
        searchDao.upsertRecentSearch(data.copy(
            id = data.id?.plus(2),
            text = "asd3"
        ))

        //when
        searchDao.deleteAllRecentSearch()

        //then
        val result = searchDao.getRecentSearches().first()
        assertTrue(result.isEmpty())
    }

    /**
     * specific id delete 테스트
     *
     * 데이터베이스에 특정 id RecentSearchEntity 를 삭제 해야한다.
     */
    @Test
    fun specific_id_recentSearchEntity_is_must_be_deleted_in_the_database() = runTest {
        //given
        val data = RecentSearchEntity(
            id = 1,
            text = "hi",
            createdAt = System.currentTimeMillis().toDateTimeFormat(),
        )

        searchDao.upsertRecentSearch(data)
        searchDao.upsertRecentSearch(data.copy(
            id = data.id?.plus(1),
            text = "asd"
        ))
        searchDao.upsertRecentSearch(data.copy(
            id = data.id?.plus(2),
            text = "asd3"
        ))

        //when
        searchDao.deleteRecentSearch(2)

        //then
        val result = searchDao.getRecentSearches().first()
        assertTrue(result.find { it.id == 2 } == null)
    }
}