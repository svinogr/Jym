package info.upump.mycompose.ui.screens.myworkouts.viewmodel


import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class WorkoutVM() : BaseVMWithStateLoad(), VMInterface<Workout> {
    companion object {
        val vmOnlyForPreview by lazy {
            object : VMInterface<Workout> {

                private val _workout = MutableStateFlow(Workout().apply {
                    day = Day.TUESDAY
                    title = "Preview Workout"
                    comment = "это Preview Workout"
                })

                override val item: StateFlow<Workout> = _workout.asStateFlow()

                private val _title = MutableStateFlow(_workout.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_workout.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_workout.value.startDate)
                override val startDate: StateFlow<Date>  = _startDate

                private val _finishDate = MutableStateFlow(_workout.value.finishDate)
                override val finishDate: StateFlow<Date> = _finishDate

                private val _day = MutableStateFlow(_workout.value.day)
                override val day: StateFlow<Day> = _day

                private val _img = MutableStateFlow(_workout.value.day.toString())
                override val imgOption: StateFlow<String> = _img.asStateFlow()

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save() {
                    TODO("Not yet implemented")
                }

                override fun updateTitle(title: String) {
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

                override fun updateDay(dayN: Day) {
                    _day.update { dayN }
                    }
                }
            }
        }
    private val _workouts = MutableStateFlow<List<Workout>>(listOf())
    val itemList: StateFlow<List<Workout>> = _workouts.asStateFlow()

    private val _workout = MutableStateFlow(Workout())
    override val item: StateFlow<Workout> = _workout.asStateFlow()

    private val _title = MutableStateFlow(_workout.value.title)
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _comment = MutableStateFlow(_workout.value.comment)
    override val comment: StateFlow<String> = _comment.asStateFlow()

    private val _startDate = MutableStateFlow(_workout.value.startDate)
    override val startDate: StateFlow<Date>  = _startDate

    private val _finishDate = MutableStateFlow(_workout.value.finishDate)
    override val finishDate: StateFlow<Date> = _finishDate

    private val _day = MutableStateFlow(_workout.value.day)
    override val day: StateFlow<Day> = _day

    private val _img = MutableStateFlow(_workout.value.day.toString())
    override val imgOption: StateFlow<String> = _img.asStateFlow()

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                _workout.update { Workout() }
                return@launch
            }

            WorkoutRepo.get().getBy(id).map {
                Workout.mapFromDbEntity(it)
            }.collect {
                _workout.value = it
            }
        }
    }

    override fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save", "${item.value}")
            val c = Workout.mapToEntity(item.value)
            Log.d("save", "$c")

            // CycleRepo.get().save(Cycle.mapToEntity(cycle.value))
        }
    }

    override fun updateTitle(titlen: String) {
        _workout.update {
            Workout.copy(it).apply { title = titlen }
        }
    }

    override fun updateImage(imgStr: String) {
        _img.update {
            imgStr
        }
    }

    override fun updateStartDate(date: Date) {
        _workout.update {
            Workout.copy(it).apply {
                startDate = date
            }
        }
    }

    override fun updateFinishDate(date: Date) {
        _workout.update {
            Workout.copy(it).apply { finishDate = date }
        }
    }

    override fun updateComment(commentN: String) {
        Log.d("DAY", "$commentN")
        _workout.update {
            Workout.copy(it).apply { comment = commentN }
        }
    }

    override fun updateDay(dayN: Day) {
        Log.d("DAY", "$dayN")
        _workout.update {
            Workout.copy(it).apply { day = dayN }
        }
    }
}

interface VMInterface<T> {
    val item: StateFlow<T>
    val imgOption: StateFlow<String>
    val title: StateFlow<String>
    val comment: StateFlow<String>
    val startDate: StateFlow<Date>
    val finishDate: StateFlow<Date>
    val day: StateFlow<Day>

    fun getBy(id: Long)
    fun save()
    fun updateTitle(title: String)
    fun updateImage(imgStr: String)
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
    fun updateComment(comment: String)
    fun updateDay(it: Day)
}