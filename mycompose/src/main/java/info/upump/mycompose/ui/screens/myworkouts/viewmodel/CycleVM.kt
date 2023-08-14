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
import kotlinx.coroutines.launch
import java.util.Date

class CycleVM : BaseVMWithStateLoad(), CycleVMInterface {
    companion object{
        val vmOnlyForPreview by lazy {
            object : CycleVMInterface{
                override val _cycles: MutableStateFlow<List<Cycle>>
                    get() = MutableStateFlow(mutableListOf(Cycle().apply { title = "2" }, Cycle().apply { title = "2" }))
                override val cycles: StateFlow<List<Cycle>>
                    get() = _cycles
                override val _cycle: MutableStateFlow<Cycle>
                    get() = MutableStateFlow(Cycle().apply { defaultImg = "drew"
                    title = "Новая"
                    comment = "это коммент"})

                override val cycle: StateFlow<Cycle>
                    get() = _cycle

                override fun getAllPersonal() {
                    TODO("Not yet implemented")
                }

                override fun getCycle(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun updateTitle(it: String) {
                    TODO("Not yet implemented")
                }

                override fun updateImg(imgStr: String) {
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

    override  val _cycles = MutableStateFlow<List<Cycle>>(listOf())
    override val cycles: StateFlow<List<Cycle>> = _cycles.asStateFlow()

    override val _cycle = MutableStateFlow(Cycle())
    override val cycle: StateFlow<Cycle> = _cycle.asStateFlow()

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
                _cycle.value = Cycle().apply { title = "" }
                Log.d("getCycle", "${cycle.value}")
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
            CycleRepo.get().save(Cycle.mapToEntity(cycle.value))
        }
    }

    override fun updateTitle(it: String) {
        //   viewModelScope.launch(Dispatchers.IO) {
        Log.d("updateVM", "1 $cycle")
        val c = cycle.value.copy()
        c.title = it
        _cycle.value = c
        // _cycle.emit(cycle)
        Log.d("updateVM", "2 ${_cycle.value}")
        // teststr  = it
        //   }
    }

     override fun updateImg(imgStr: String) {
         val c = cycle.value.copy()
         c.image = imgStr
         _cycle.value = c
     }

    override fun updateStartDate(date: Date) {
        Log.d("start date", "$date")
        val c = cycle.value.copy()
        c.startDate = date
        _cycle.value = c
       }

    override fun updateFinishDate(date: Date) {
        Log.d("start date", "$date")

        val c = cycle.value.copy()
        c.finishDate = date
        Log.d("start date", "${c.finishDate}")
        _cycle.value = c

    }

    override fun updateComment(comment: String) {
        val c = cycle.value.copy()
        c.comment = comment
        Log.d("start date", "${c}")

        _cycle.value = c
    }
}

interface CycleVMInterface {
    val _cycles: MutableStateFlow<List<Cycle>>
    val cycles: StateFlow<List<Cycle>>

    val _cycle: MutableStateFlow<Cycle>
    val cycle: StateFlow<Cycle>

    fun getAllPersonal()

    fun getCycle(id: Long)


    fun saveCycle() {
    }

    fun updateTitle(title: String)
    fun updateImg(imgStr: String)
    fun updateStartDate(date: Date)
    fun updateFinishDate(date: Date)
    fun updateComment(comment: String)

}