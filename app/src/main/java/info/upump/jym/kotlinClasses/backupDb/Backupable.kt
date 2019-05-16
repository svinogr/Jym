package info.upump.jym.kotlinClasses.backupDb

interface Backupable {
    companion object const {
        const val WRITE_TO_FILE = 1
        const val WRITE_TO_MAIL = 2
    }

    fun toBackup(to: Int)
    fun fromBackup()
}