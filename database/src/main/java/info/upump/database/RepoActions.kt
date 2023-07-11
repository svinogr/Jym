package info.upump.database

interface RepoActions<T> {
    fun getAll(): List<T>
    fun save(item: T): T
    fun getAllPersonal(): List<T>
    fun getAllDefault(): List<T>


}

