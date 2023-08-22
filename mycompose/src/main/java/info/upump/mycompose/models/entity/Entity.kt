package info.upump.mycompose.models.entity

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
const val FORMAT_DATE = "yyyy-MM-dd"
abstract class Entity(
    var id: Long = 0,
    var title: String = "",
    var startDate: Date = Date(),
    var finishDate: Date = Date(),
    var comment: String = "",
    var parentId: Long = 0,
) {

    companion object{
        fun formatDateToString(date: Date): String
        {
            val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return simpleDateFormat.format(date)
        }

        fun formatStringToDate(stringDate: String): Date {
            val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            var date = Date()
            try {
                date = simpleDateFormat.parse(stringDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }
    }
    val startStringFormatDate: String
        get() {
    /*        val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return simpleDateFormat.format(startDate!!)*/
            return formatDateToString(startDate)
        }

    fun setStartDate(stringDate: String) {
    /*    val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        try {
            startDate = simpleDateFormat.parse(stringDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }*/
        startDate =  formatStringToDate(stringDate)
    }

    val finishStringFormatDate: String
        get() {
      /*      val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return simpleDateFormat.format(finishDate)*/
            return formatDateToString(finishDate)

        }

    fun setFinishDate(stringDate: String) {
       /* val simpleDateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        try {
            finishDate = simpleDateFormat.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }*/
        finishDate = formatStringToDate(stringDate)
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