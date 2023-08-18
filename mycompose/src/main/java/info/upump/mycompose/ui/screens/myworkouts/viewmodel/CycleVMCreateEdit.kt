package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class CycleVMCreateEdit : BaseVMWithStateLoad(), VMInterface<Cycle> {
    companion object {
        val vmOnlyForPreview by lazy {
            object : VMInterface<Cycle> {
                private val _itemList = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                private val _cycle = MutableStateFlow(Cycle().apply {
                    defaultImg = "drew"
                    title = "Preview"
                    comment = "это Preview"
                })

                override val item: StateFlow<Cycle> = _cycle.asStateFlow()

                private val _title = MutableStateFlow(_cycle.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_cycle.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_cycle.value.startDate)
                override val startDate: StateFlow<Date> = _startDate

                private val _finishDate = MutableStateFlow(_cycle.value.finishDate)
                override val finishDate: StateFlow<Date> = _finishDate


                override val day: StateFlow<Day> =MutableStateFlow(Day.MONDAY).asStateFlow()

                private val _img = MutableStateFlow(_cycle.value.image)
                override val imgOption: StateFlow<String> = _img.asStateFlow()


                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save() {
                    TODO("Not yet implemented")
                }

                override fun updateTitle(it: String) {
                    TODO("Not yet implemented")
                }

                override fun updateImage(imgStr: String) {
                    TODO("Not yet implemented")
                }

                override fun updateStartDate(date: Date) {
                    TODO("Not yet implemented")
                }

                override fun updateFinishDate(date: Date) {
                    TODO("Not yet implemented")
                }

                override fun updateComment(comment: String) {
                    TODO("Not yet implemented")
                }

                override fun updateDay(it: Day) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    val itemList: StateFlow<List<Cycle>> = _cycleList.asStateFlow()

    private val _cycle = MutableStateFlow(Cycle())
    override val item: StateFlow<Cycle> = _cycle.asStateFlow()

    private val _title = MutableStateFlow(item.value.title)
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _comment = MutableStateFlow(item.value.comment)
    override val comment: StateFlow<String> = _comment.asStateFlow()

    private val _startDate = MutableStateFlow(item.value.startDate)
    override val startDate: StateFlow<Date> = _startDate

    private val _finishDate = MutableStateFlow(item.value.finishDate)
    override val finishDate: StateFlow<Date> = _finishDate

    override val day: StateFlow<Day> = MutableStateFlow(Day.MONDAY).asStateFlow()

    private val _img = MutableStateFlow(item.value.image)
    override val imgOption: StateFlow<String> = _img.asStateFlow()

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                _cycle.update { Cycle()



                }
                return@launch
            }

            CycleRepo.get().getBy(id).map {
                Cycle.mapFromDbEntity(it)
            }.collect {
                _cycle.value = it
            }
        }
    }

    override fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save", "${_cycle.value}")
            val c = Cycle.mapToEntity(_cycle.value)
            c.img = imgOption.value
            Log.d("save", "$c")

            // CycleRepo.get().save(Cycle.mapToEntity(cycle.value))
        }
    }

    override fun updateTitle(title: String) {
        _title.update { title }
    }

    override fun updateImage(imgStr: String) {
        _img.update { imgStr }
    }

    override fun updateStartDate(date: Date) {
        _startDate.update { date }
    }

    override fun updateFinishDate(date: Date) {
        _finishDate.update { date }
    }

    override fun updateComment(comment: String) {
        _comment.update { comment }
    }

    override fun updateDay(it: Day) {
        TODO("Not yet implemented")
    }
}
