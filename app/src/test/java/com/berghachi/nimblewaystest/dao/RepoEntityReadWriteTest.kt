package com.berghachi.nimblewaystest.dao

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.berghachi.nimblewaystest.data.local.LocalDataSource
import com.berghachi.nimblewaystest.data.local.dao.RepoDao
import com.berghachi.nimblewaystest.data.local.model.Owner
import com.berghachi.nimblewaystest.data.local.model.Repo
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class RepoEntityReadWriteTest {
    private lateinit var repoDao: RepoDao
    private lateinit var db: LocalDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LocalDataSource::class.java
        ).allowMainThreadQueries().build()
        repoDao = db.getRepoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun ` Test case when insert repo to favoris `() {
        val repo: Repo = Repo(
            1,
            Owner(
                avatarUrl = "https://avatars3.githubusercontent.com/u/9597329?v=4",
                idOwner = 248207352,
                login = "bamlab",
                url = "https://api.github.com/users/bamlab"
            ),
            fullName = "bamlab/vscode-evenstorming",
            name = "vscode-evenstorming",
            language = "TypeScript",
            nodeId = "155551",
            html_url = "https://github.com/bamlab/conceptor",
            isFavorite = true

        )

        runBlocking {
            repoDao.addRepoToFavorite(repo)
            val isInserted = repoDao.isRepoIsExist(repo.id)
            Assert.assertEquals(true, isInserted)
        }


    }

    @Test
    @Throws(Exception::class)
    fun ` Test case when delete repo from favoris `() {
        val repo: Repo = Repo(
            1,
            Owner(
                avatarUrl = "https://avatars3.githubusercontent.com/u/9597329?v=4",
                idOwner = 248207352,
                login = "bamlab",
                url = "https://api.github.com/users/bamlab"
            ),
            fullName = "bamlab/vscode-evenstorming",
            name = "vscode-evenstorming",
            language = "TypeScript",
            nodeId = "155551",
            html_url = "https://github.com/bamlab/conceptor",
            isFavorite = true

        )

        runBlocking {
            repoDao.addRepoToFavorite(repo)
            val isInserted = repoDao.isRepoIsExist(repo.id)
            Assert.assertEquals(true, isInserted)
            repoDao.deleteRepo(repo)

            val isDeleted = repoDao.isRepoIsExist(repo.id)
            Assert.assertEquals(false, isDeleted)
        }


    }
}

