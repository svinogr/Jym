package info.upump.jym.db

interface RepoActions<T> {
    fun getAll(): List<T>
    fun save(item: T): T
}