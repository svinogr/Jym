package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class CycleVM() : BaseVMWithStateLoad(), CycleVMInterface {
    companion object {
        val vmOnlyForPreview by lazy {
            object : CycleVMInterface {
                private val _cycles = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                override val cycles: StateFlow<List<Cycle>> = _cycles

                private val _cycle = MutableStateFlow(Cycle().apply {
                    defaultImg = "drew"
                    title = "Preview"
                    comment = "это Preview"
                })

                override val cycle: StateFlow<Cycle> = _cycle
                override val img: StateFlow<String>
                    get() = TODO("Not yet implemented")

                override fun getAllPersonal() {
                    TODO("Not yet implemented")
                }

                override fun getCycle(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun saveCycle() {
                    TODO("Not yet implemented")
                }

                override fun updateTitle(it: String) {
                    TODO("Not yet implemented")
                }

                override fun updateImgage(imgStr: String) {
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
            }
        }
    }

    private val _cycles = MutableStateFlow<List<Cycle>>(listOf())
    override val cycles: StateFlow<List<Cycle>> = _cycles.asStateFlow()

    private val _cycle = MutableStateFlow(Cycle())
    override val cycle: StateFlow<Cycle> = _cycle.asStateFlow()

    private val _img = MutableStateFlow<String>("")
    override val img: StateFlow<String> = _img.asStateFlow()

    override fun getAllPersonal() {
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val list = CycleRepo.get().getAllPersonal()
            list.map {
                it.map {
                    Cycle.mapFromDbEntity(it)
                }
            }.collect {
                _cycles.value = it
            }
            _stateLoading.value = false
        }
    }

    override fun getCycle(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                _cycle.update { Cycle() }
                return@launch
            }

            CycleRepo.get().getBy(id).map {
                Cycle.mapFromDbEntity(it)
            }.collect {
                _cycle.value = it
            }
        }
    }

    override fun saveCycle() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save", "${cycle.value}")
            val c = Cycle.mapToEntity(cycle.value)
            c.img = img.value
            Log.d("save", "$c")

            // CycleRepo.get().save(Cycle.mapToEntity(cycle.value))
        }
    }

    override fun updateTitle(titlen: String) {
        _cycle.update {
            Cycle.copy(it).apply { title = titlen }
        }
    }

    override fun updateImgage(imgStr: String) {
            _img.update {imgStr
            }
    }
    override fun updateStartDate(date: Date) {
        _cycle.update {
            Cycle.copy(it).apply {
                startDate = date
            }
        }
    }
    override fun updateFinishDate(date: Date) {
        _cycle.update {
            Cycle.copy(it).apply { finishDate = date }
        }
    }

    override fun updateComment(commentN: String) {
        _cycle.update {
            Cycle.copy(it).apply { comment = commentN }
        }
    }
}

interface CycleVMInterface {
    val cycles: StateFlow<List<Cycle>>
    val cycle: StateFlow<Cycle>
    val img: StateFlow<String>

    fun getAllPersonal()
    fun getCycle(id: Long)
    fun saveCycle()
    fun updateTitle(title: String)
    fun updateImgage(imgStr: String)
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
    fun updateComment(comment: String)
}