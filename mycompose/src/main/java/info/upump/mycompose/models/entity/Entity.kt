package info.upump.mycompose.models.entity

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class Entity(
    open var id: Long = 0,
    val formatDate: String = "yyyy-MM-dd",
    var startDate: Date? = Date(),
    var finishDate: Date? = Date(),
    var comment: String? = null,
    var parentId: Long = 0
) {

    val startStringFormatDate: String
        get() {
            val simpleDateFormat = SimpleDateFormat(formatDate, Locale.getDefault())
            return simpleDateFormat.format(startDate)
        }

    fun setStartDate(stringDate: String?) {
        val simpleDateFormat = SimpleDateFormat(formatDate, Locale.getDefault())
        try {
            startDate = simpleDateFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    val finishStringFormatDate: String
        get() {
            val simpleDateFormat = SimpleDateFormat(formatDate, Locale.getDefault())
            return simpleDateFormat.format(finishDate)
        }

    fun setFinishDate(stringDate: String?) {
        val simpleDateFormat = SimpleDateFormat(formatDate, Locale.getDefault())
        try {
            finishDate = simpleDateFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Entity) return false
        return id == o.id
    }

    override fun hashCode(): Int {
        return (id xor (id ushr 32)).toInt()
    }
}