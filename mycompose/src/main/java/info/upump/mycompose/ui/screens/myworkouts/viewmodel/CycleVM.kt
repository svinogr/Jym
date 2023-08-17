package info.upump.mycompose.ui.screens.myworkouts.viewmodel

import android.util.Log
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

class CycleVM : BaseVMWithStateLoad(), VMInterface<Cycle> {
    companion object {
        val vmOnlyForPreview by lazy {
            object : VMInterface<Cycle> {
                private val _itemList = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                override val itemList: StateFlow<List<Cycle>> = _itemList

                private val _item = MutableStateFlow(Cycle().apply {
                    defaultImg = "drew"
                    title = "Preview"
                    comment = "это Preview"
                })

                override val item: StateFlow<Cycle> = _item

                private val _imageOption = MutableStateFlow("")
                override val imgOption: StateFlow<String> = _imageOption.asStateFlow()

                override fun getAllPersonal() {
                    TODO("Not yet implemented")
                }

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save() {
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

                override fun updateDay(it: Day) {
                    TODO("Not yet implemented")
                }
            }
        }
    }

    private val _itemList = MutableStateFlow<List<Cycle>>(listOf())
    override val itemList: StateFlow<List<Cycle>> = _itemList.asStateFlow()

    private val _item = MutableStateFlow(Cycle())
    override val item: StateFlow<Cycle> = _item.asStateFlow()

    private val _img = MutableStateFlow<String>("")
    override val imgOption: StateFlow<String> = _img.asStateFlow()

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
                _itemList.value = it
            }
            _stateLoading.value = false
        }
    }

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                _item.update { Cycle() }
                return@launch
            }

            CycleRepo.get().getBy(id).map {
                Cycle.mapFromDbEntity(it)
            }.collect {
                _item.value = it
            }
        }
    }

    override fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save", "${_item.value}")
            val c = Cycle.mapToEntity(_item.value)
            c.img = imgOption.value
            Log.d("save", "$c")

            // CycleRepo.get().save(Cycle.mapToEntity(cycle.value))
        }
    }

    override fun updateTitle(titlen: String) {
        _item.update {
            Cycle.copy(it).apply { title = titlen }
        }
    }

    override fun updateImgage(imgStr: String) {
            _img.update {imgStr
            }
    }
    override fun updateStartDate(date: Date) {
        _item.update {
            Cycle.copy(it).apply {
                startDate = date
            }
        }
    }
    override fun updateFinishDate(date: Date) {
        _item.update {
            Cycle.copy(it).apply { finishDate = date }
        }
    }

    override fun updateComment(commentN: String) {
        _item.update {
            Cycle.copy(it).apply { comment = commentN }
        }
    }

    override fun updateDay(it: Day) {
        TODO("Not yet implemented")
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