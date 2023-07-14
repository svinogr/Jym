package info.upump.database

interface RepoActions<T> {
    fun getAll(): List<T>
    fun save(item: T): T
    fun getAllPersonal(): List<T>
    fun getAllDefault(): List<T>
    fun getBy(id: Long): T
    fun getAllByParent(id: Long): List<T>
}

