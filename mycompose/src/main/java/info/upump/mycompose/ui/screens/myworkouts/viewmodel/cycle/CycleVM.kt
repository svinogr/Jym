package info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// для списка всех cycles
class CycleVM : BaseVMWithStateLoad() {
    private val _cycleList = MutableStateFlow<List<Cycle>>(listOf())
    val cycleList: StateFlow<List<Cycle>> = _cycleList.asStateFlow()
    val cycleRepo = CycleRepo.get()

    suspend fun getAllPersonal() {
        Log.d("getAllPersonal", "getAllPersonal")
        _stateLoading.value = true

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val list = cycleRepo.getAllFullEntityPersonal()
            list.map {
                it.map { c ->
                    Cycle.mapFullFromDbEntity(c)
                }
            }.collect {
                _cycleList.value = it
                _stateLoading.value = false
            }
        }
    }

    fun delete(context: Context, image: String,  id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTempImg( image, context)
            cycleRepo.delete(id)
        }
    }

    private fun deleteTempImg(tempImage: String, context: Context) {
        if (tempImage.isBlank()) return
        val file = Uri.parse(tempImage).toFile()
        if (file.exists()) {
            file.delete()
        }
    }
}