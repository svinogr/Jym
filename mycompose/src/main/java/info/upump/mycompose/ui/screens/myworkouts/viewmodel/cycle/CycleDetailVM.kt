package info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle

import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMDetailInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CycleDetailVM : BaseVMWithStateLoad(), VMDetailInterface<Cycle, Workout> {
    private var _cycle = MutableStateFlow(Cycle())
    override val item: StateFlow<Cycle> = _cycle

    private var _workouts = MutableStateFlow<List<Workout>>(mutableListOf())
    override val subItems: StateFlow<List<Workout>> = _workouts

    private val _id = MutableStateFlow(_cycle.value.id)
    override val id: StateFlow<Long> = _id

    private val _image = MutableStateFlow(_cycle.value.image)
    override val img: StateFlow<String> = _image

    private val _imageDefault = MutableStateFlow(_cycle.value.imageDefault)
    override val imgDefault: StateFlow<String> = _imageDefault


    private val _title = MutableStateFlow(_cycle.value.title)
    override val title: StateFlow<String> = _title

    private val _comment = MutableStateFlow(_cycle.value.comment)
    override val comment: StateFlow<String> = _comment

    private val _startDate = MutableStateFlow(_cycle.value.startStringFormatDate)
    override val startDate: StateFlow<String> = _startDate

    private val _finishDate = MutableStateFlow(_cycle.value.finishStringFormatDate)
    override val finishDate: StateFlow<String> = _finishDate

    override val day: StateFlow<Day> = MutableStateFlow(Day.MONDAY)

    override fun getInitialItem(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLoading.value = true

            CycleRepo.get().getBy(id).map {
                Cycle.mapFromDbEntity(it)
            }.collect { cycle ->
                updateFields(cycle)
                WorkoutRepo.get().getAllByParent(id).map {
                    it.map { w -> Workout.mapFromDbEntity(w) }
                }.collect { list ->
                    _workouts.value = list
                    _cycle.value = cycle
                    _stateLoading.value = false
                }
            }
        }
    }

    private fun updateFields(cycle: Cycle) {
        _id.update { cycle.id }
        _title.update { cycle.title }
        _comment.update { cycle.comment }
        _startDate.update { cycle.startStringFormatDate }
        _finishDate.update { cycle.finishStringFormatDate }
        _image.update { cycle.image }
        _imageDefault.update { cycle.imageDefault }
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : VMDetailInterface<Cycle, Workout> {
                private val _itemList = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                private val _cycle = MutableStateFlow(Cycle().apply {
                    imageDefault = "drew"
                    image = ""
                    title = "Preview"
                    comment = "это Preview"
                })

                override val item: StateFlow<Cycle> = _cycle

                private var _workouts = MutableStateFlow<List<Workout>>(listOf(
                    Workout(
                        isWeekEven = false, isDefaultType = false,
                        isTemplate = false, day = Day.FRIDAY, exercises = listOf()
                    ).apply { title = "Новая" }, Workout(
                        isWeekEven = false, isDefaultType = false,
                        isTemplate = false, day = Day.THURSDAY, exercises = listOf()
                    ).apply { title = "Новая 1" }, Workout(

                        isWeekEven = false, isDefaultType = false,
                        isTemplate = false, day = Day.MONDAY, exercises = listOf()
                    ).apply { title = "Новая 2" }
                ))
                override val subItems: StateFlow<List<Workout>> = _workouts


                private val _id = MutableStateFlow(_cycle.value.id)
                override val id: StateFlow<Long> = _id.asStateFlow()

                private val _title = MutableStateFlow(_cycle.value.title)
                override val title: StateFlow<String> = _title.asStateFlow()

                private val _comment = MutableStateFlow(_cycle.value.comment)
                override val comment: StateFlow<String> = _comment.asStateFlow()

                private val _startDate = MutableStateFlow(_cycle.value.startStringFormatDate)
                override val startDate: StateFlow<String> = _startDate

                private val _finishDate = MutableStateFlow(_cycle.value.finishStringFormatDate)
                override val finishDate: StateFlow<String> = _finishDate


                override val day: StateFlow<Day> = MutableStateFlow(Day.MONDAY).asStateFlow()
                override fun getInitialItem(id: Long) {
                    TODO("Not yet implemented")
                }

                private val _img = MutableStateFlow(_cycle.value.image)
                override val img: StateFlow<String> = _img.asStateFlow()

                private val _imgDefault = MutableStateFlow(_cycle.value.imageDefault)
                override val imgDefault: StateFlow<String> = _imgDefault.asStateFlow()
            }
        }
    }
}