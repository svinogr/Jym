package info.upump.database

import kotlinx.coroutines.flow.Flow

interface RepoActionsSpecific<T, R> : RepoActions<T> {
    fun getFullEntityBy(id: Long): Flow<R>
    fun getAllFullEntityByParent(id: Long): Flow<List<R>>
    fun getAllFullEntity(): Flow<List<R>>
}