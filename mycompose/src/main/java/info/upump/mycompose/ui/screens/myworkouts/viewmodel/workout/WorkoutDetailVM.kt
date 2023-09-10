package info.upump.mycompose.ui.screens.myworkouts.viewmodel.workout

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.entities.WorkoutFullEntity
import info.upump.database.repo.ExerciseRepo
import info.upump.database.repo.WorkoutRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.models.entity.ExerciseDescription
import info.upump.mycompose.models.entity.TypeMuscle
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.WorkoutDetailVMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVMInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class WorkoutDetailVM : BaseVMWithStateLoad(), WorkoutDetailVMInterface {
    private val workoutRepo = WorkoutRepo.get()

    private val _workout = MutableStateFlow(Workout())

    override val item: StateFlow<Workout> = _workout

    private val _exercises = MutableStateFlow<List<Exercise>>(listOf())

    override val subItems: StateFlow<List<Exercise>> = _exercises

    private val _id = MutableStateFlow(0L)

    override val id: StateFlow<Long> = _id.asStateFlow()

    private
    val _title = MutableStateFlow("")

    override
    val title: StateFlow<String> =
        _title.asStateFlow()

    private
    val _comment = MutableStateFlow(
        ""
    )

    override
    val comment: StateFlow<String> =
        _comment.asStateFlow()

    private
    val _startDate =
        MutableStateFlow(
            ""
        )

    override
    val startDate: StateFlow<String> =
        _startDate

    private
    val _finishDate =
        MutableStateFlow(
            ""
        )

    override
    val finishDate: StateFlow<String> =
        _finishDate


    private
    val _day = MutableStateFlow(Day.TUESDAY)

    override
    val day: StateFlow<Day> = _day.asStateFlow()

    val _isEven = MutableStateFlow<Boolean>(true)

    override val isEven: StateFlow<Boolean> = _isEven

    /*    override fun getBy(id: Long) {
            _stateLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {

                val wFlow = WorkoutRepo.get().getBy(id).map {
                    Workout.mapFromDbEntity(it)
                }

                val eflow = ExerciseRepo.get().getAllFullEntityByParent(id).map {
                    it.map {
                        Exercise.mapFromFullDbEntity(it)
                    }
                }

                wFlow.zip(eflow) { workout, exercise ->
                   workout.exercises = exercise
                    return@zip workout
                }.collect { workout ->
                    _workout.update { workout }
                    _id.update { workout.id }
                    _title.update { workout.title }
                    _comment.update { workout.comment }
                    _startDate.update { workout.startStringFormatDate }
                    _finishDate.update { workout.finishStringFormatDate }
                    _day.update { workout.day }
                    _isEven.update { workout.isWeekEven }
                    _exercises.update { workout.exercises }
                    _exercises.update { workout.exercises }

                    _stateLoading.value = false
                }

            }

        }*/

    override fun getBy(id: Long) {
        _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {

            workoutRepo.getFullEntityBy(id).map {
               Workout.mapFromFullDbEntity(it)
            }.collect{workout ->
                _workout.update { workout }
                _id.update { workout.id }
                _title.update { workout.title }
                _comment.update { workout.comment }
                _startDate.update { workout.startStringFormatDate }
                _finishDate.update { workout.finishStringFormatDate }
                _day.update { workout.day }
                _isEven.update { workout.isWeekEven }
                _exercises.update { workout.exercises }

                _stateLoading.value = false

            }
         /*
            val wFlow = WorkoutRepo.get().getBy(id).map {
                Workout.mapFromDbEntity(it)
            }

            val eflow = ExerciseRepo.get().getAllFullEntityByParent(id).map {
                it.map {
                    Exercise.mapFromFullDbEntity(it)
                }
            }

            wFlow.zip(eflow) { workout, exercise ->
                workout.exercises = exercise
                return@zip workout
            }.collect { workout ->
                _workout.update { workout }
                _id.update { workout.id }
                _title.update { workout.title }
                _comment.update { workout.comment }
                _startDate.update { workout.startStringFormatDate }
                _finishDate.update { workout.finishStringFormatDate }
                _day.update { workout.day }
                _isEven.update { workout.isWeekEven }
                _exercises.update { workout.exercises }
                _exercises.update { workout.exercises }

                _stateLoading.value = false
            }*/

        }

    }

    override fun delete(it: Long) {

    }

    override fun deleteSub(it: Long) {

    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : WorkoutDetailVMInterface {
                private val _workout = MutableStateFlow((Workout().apply {
                    title = "Preview3"
                    isWeekEven = true
                    isDefaultType = true
                }))

                override val item: StateFlow<Workout> = _workout

                private val _exercises = MutableStateFlow(listOf(
                    Exercise().apply {
                        typeMuscle = TypeMuscle.ABS
                        isDefaultType = true
                        isTemplate = true
                        setsList = mutableListOf()
                        descriptionId = 1
                        exerciseDescription = ExerciseDescription().apply {
                            img = "nach1"
                            defaultImg = "nach1"
                            title = "Новое упраж"
                            id = descriptionId
                        }
                    },
                    Exercise().apply {
                        typeMuscle = TypeMuscle.BACK
                        isDefaultType = true
                        isTemplate = true
                        setsList = mutableListOf()
                        descriptionId = 2
                        exerciseDescription = ExerciseDescription().apply {
                            img = "nach1"
                            defaultImg = "nach1"
                            title = "Новое упраж"
                            id = descriptionId
                        }
                    },
                    Exercise().apply {
                        typeMuscle = TypeMuscle.CALVES
                        isDefaultType = true
                        isTemplate = true
                        setsList = mutableListOf()
                        descriptionId = 3
                        exerciseDescription = ExerciseDescription().apply {
                            img = "nach1"
                            defaultImg = "nach1"
                            title = "Новое упраж"
                            id = descriptionId
                        }
                    }
                ))

                override val subItems: StateFlow<List<Exercise>> = _exercises


                private val _id = MutableStateFlow(_workout.value.id)

                override val id: StateFlow<Long> = _id.asStateFlow()

                private
                val _title = MutableStateFlow(_workout.value.title)

                override
                val title: StateFlow<String> =
                    _title.asStateFlow()

                private
                val _comment = MutableStateFlow(
                    _workout.value.comment
                )

                override
                val comment: StateFlow<String> =
                    _comment.asStateFlow()

                private
                val _startDate =
                    MutableStateFlow(
                        _workout.value.startStringFormatDate
                    )

                override
                val startDate: StateFlow<String> =
                    _startDate

                private
                val _finishDate =
                    MutableStateFlow(
                        _workout.value.finishStringFormatDate
                    )

                override
                val finishDate: StateFlow<String> =
                    _finishDate

                override fun getBy(
                    id: Long
                ) {
                    TODO(
                        "Not yet implemented"
                    )
                }

                override fun delete(it: Long) {
                    TODO("Not yet implemented")
                }

                override fun deleteSub(it: Long) {
                    TODO("Not yet implemented")
                }

                private
                val _day = MutableStateFlow(_workout.value.day)

                override
                val day: StateFlow<Day> = _day.asStateFlow()

                val _isEven = MutableStateFlow<Boolean>(true)

                override val isEven: StateFlow<Boolean> = _isEven
            }
        }
    }
}
