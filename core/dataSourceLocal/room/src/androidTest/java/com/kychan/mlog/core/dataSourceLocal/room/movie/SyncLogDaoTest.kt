package com.kychan.mlog.core.dataSourceLocal.room.movie

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kychan.mlog.core.common.extenstions.toDateFormat
import com.kychan.mlog.core.dataSourceLocal.room.MlogDatabase
import com.kychan.mlog.core.dataSourceLocal.room.dao.SyncLogDao
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogEntity
import com.kychan.mlog.core.dataSourceLocal.room.model.SyncLogType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SyncLogDaoTest {

    private lateinit var syncLogDao: SyncLogDao
    private lateinit var db: MlogDatabase

    @Before
    fun createDb() = runTest{
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MlogDatabase::class.java,
        ).build()
        syncLogDao = db.syncLogDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /**
     * unique constraint 검증 테스트
     *
     * 데이터베이스에 다른 id를 가지면서 동일한 type이 존재 할 경우
     *
     * 데이터베이스에 저장 돼서는 안된다.
     *
     * SQLiteConstraintException을 발생시켜야 한다.
     */
    @Test
    fun if_the_id_is_not_same_and_the_type_is_same_it_is_must_be_not_stored_in_the_database() = runTest {
        //given
        val data = SyncLogEntity(
            id = 1,
            type = SyncLogType.Mlog_Movie,
            nextKey = 1,
            createdAt = System.currentTimeMillis().toDateFormat(),
            updatedAt = System.currentTimeMillis().toDateFormat(),
        )
        syncLogDao.upsertSyncLog(data)

        //when
        val exception = runCatching {
            syncLogDao.upsertSyncLog(data.copy(id = data.id + 1))
        }.exceptionOrNull()

        //then
        assertTrue(exception is SQLiteConstraintException)
    }

    /**
     * update 테스트
     *
     * 데이터베이스에 동일한 type, id가 존재 하면 update를 해야한다.
     */
    @Test
    fun if_the_id_is_same_and_the_type_is_same_it_is_must_be_updated_in_the_database() = runTest {
        //given
        val data = SyncLogEntity(
            id = 1,
            type = SyncLogType.Mlog_Movie,
            nextKey = 1,
            createdAt = System.currentTimeMillis().toDateFormat(),
            updatedAt = System.currentTimeMillis().toDateFormat(),
        )
        syncLogDao.upsertSyncLog(data)
        val updatedData = data.copy(
            nextKey = data.nextKey+1,
            createdAt = System.currentTimeMillis().toDateFormat(),
            updatedAt = System.currentTimeMillis().toDateFormat(),
        )

        //when
        syncLogDao.upsertSyncLog(updatedData)

        //then
        val result = syncLogDao.getSyncLog(SyncLogType.Mlog_Movie)
        assertTrue(result == updatedData)
    }
}