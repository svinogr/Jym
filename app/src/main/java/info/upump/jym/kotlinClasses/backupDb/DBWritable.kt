package info.upump.jym.kotlinClasses.backupDb

interface DBWritable {
    fun writeTo(curentDB: String)
    fun writeToFile()
    fun writeToMail()
}