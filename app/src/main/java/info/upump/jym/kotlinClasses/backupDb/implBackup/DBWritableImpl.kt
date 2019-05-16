package info.upump.jym.kotlinClasses.backupDb.implBackup

import info.upump.jym.kotlinClasses.backupDb.DBWritable

class DBWritableImpl : DBWritable {
    override fun writeToMail() {
        println("do write to email")
    }

    override fun writeTo(curentDB: String) {
        println("do write to DB")
    }

    override fun writeToFile() {
        println("do write to file")

    }
}