package info.upump.jym.kotlinClasses.backupDb

interface DBReadable {
    fun readFrom(dbName: String)
    fun readFromFile(fileName: String)
}