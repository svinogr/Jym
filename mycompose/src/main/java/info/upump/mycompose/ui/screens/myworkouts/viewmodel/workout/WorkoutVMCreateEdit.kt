package info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout


import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
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

                private val _id = MutableStateFlow(item.value.id)
                override val id: StateFlow<Long> = _id.asStateFlow()

                private val _title = MutableStateFlow(_workout.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_workout.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_workout.value.startStringFormatDate)
                override val startDate: StateFlow<String> = _startDate

                private val _finishDate = MutableStateFlow(_workout.value.finishStringFormatDate)
                override val finishDate: StateFlow<String> = _finishDate

                private val _day = MutableStateFlow(_workout.value.day)
                override val day: StateFlow<Day> = _day

                override val imgDefault: StateFlow<String>    get() = TODO("Not yet implemented")
                override val isTitleError: StateFlow<Boolean>
                    get() = TODO("Not yet implemented")

                private val _img = MutableStateFlow(_workout.value.day.toString())
                override val img: StateFlow<String> = _img.asStateFlow()

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save(callback: (id: Long) -> Unit) {
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

                override fun collectToSave(): Workout {
                    TODO("Not yet implemented")
                }

                override fun isBlankFields(): Boolean {
                    TODO("Not yet implemented")
                }

                override fun updateId(id: Long) {
                    _id.update { id }
                }

                override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
                    TODO("Not yet implemented")
                }

                override fun updateImageDefault(imgStr: String) {

                }

            }
        }
    }

    private val _workouts = MutableStateFlow<List<Workout>>(listOf())
    val itemList: StateFlow<List<Workout>> = _workouts.asStateFlow()

    private val _workout = MutableStateFlow(Workout())
    override val item: StateFlow<Workout> = _workout.asStateFlow()

    private val _id = MutableStateFlow(_workout.value.id)
    override val id: StateFlow<Long> = _id.asStateFlow()

    private val _title = MutableStateFlow(_workout.value.title)
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _comment = MutableStateFlow(_workout.value.comment)
    override val comment: StateFlow<String> = _comment.asStateFlow()

    private val _startDate = MutableStateFlow(_workout.value.startStringFormatDate)
    override val startDate: StateFlow<String> = _startDate

    private val _finishDate = MutableStateFlow(_workout.value.finishStringFormatDate)
    override val finishDate: StateFlow<String> = _finishDate

    private val _day = MutableStateFlow(_workout.value.day)
    override val day: StateFlow<Day> = _day

    private val _isTitleError = MutableStateFlow(false)
    override val isTitleError: StateFlow<Boolean> = _isTitleError.asStateFlow()

    private val _img = MutableStateFlow(_workout.value.day.toString())
    override val img: StateFlow<String> = _img.asStateFlow()

    private val _isEven = MutableStateFlow(_workout.value.isWeekEven)
    val isEven: StateFlow<Boolean> = _isEven.asStateFlow()

    override val imgDefault: StateFlow<String>    get() = TODO("Not yet implemented")

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

    override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save", "${item.value}")
            val wC = collectToSave()
            val wE = Workout.mapToEntity(wC)
            wE.parent_id = parentId
            Log.d("save", "$wE")
            val save = WorkoutRepo.get().save(wE)
            callback(save._id)
        }
    }

    override fun save(callback: (id: Long) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun collectToSave(): Workout {
        val collectW = Workout().apply {
            id = _id.value
            title = _title.value
            day = _day.value
            comment = _comment.value
            isWeekEven = _isEven.value
        }

        return collectW
    }

    override fun isBlankFields(): Boolean {
        Log.d("check fielsd", "${title.value.trim().isBlank()}")
        val isBlank = title.value.trim().isBlank()
        _isTitleError.update { isBlank }

        return isBlank
    }

    override fun updateImageDefault(imgStr: String) {

    }

    override fun updateTitle(titlen: String) {
        _title.update { titlen }
    }

    override fun updateImage(imgStr: String) {
        _img.update { imgStr }
    }

    override fun updateStartDate(date: Date) {

    }

    override fun updateFinishDate(date: Date) {
    }

    override fun updateComment(commentN: String) {
        _comment.update { commentN }
    }

    override fun updateDay(dayN: Day) {
        _day.update { dayN }
    }

    fun updateEven() {
        _isEven.update { !it }
    }

    override fun updateId(id: Long) {
        _id.update { id }
    }
}
