package info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class CycleVMCreateEdit : BaseVMWithStateLoad(), CycleVMInterface {
    companion object {
        val vmOnlyForPreview by lazy {
            object : CycleVMInterface {
                private val _itemList = MutableStateFlow(
                    mutableListOf(
                        Cycle().apply { title = "Preview2" },
                        Cycle().apply { title = "Preview 3" })
                )

                private val _cycle = MutableStateFlow(Cycle().apply {
                    imageDefault = "drew"
                    title = "Preview"
                    comment = "это Preview"
                })

                override val item: StateFlow<Cycle> = _cycle.asStateFlow()

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

                override val isTitleError: StateFlow<Boolean>
                    get() = TODO("Not yet implemented")

                private val _img = MutableStateFlow(_cycle.value.image)
                override val img: StateFlow<String> = _img.asStateFlow()

                private val _imgDefault = MutableStateFlow(_cycle.value.imageDefault)
                override val imgDefault: StateFlow<String> = _imgDefault

                override fun updateImageDefault(imgStr: String) {
                    _imgDefault.update {imgStr}
                }

                override fun updateEven(it: Boolean) {
                    TODO("Not yet implemented")
                }

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save(callback: (id: Long) -> Unit) {
                    TODO("Not yet implemented")
                }

                override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
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


                override fun collectToSave(): Cycle {
                    TODO("Not yet implemented")
                }

                override fun isBlankFields(): Boolean {
                    return (title.value.trim().isEmpty())
                }

                override fun updateId(id: Long) {
                    _id.update { id }
                }

            }
        }
    }

    private val _cycle = MutableStateFlow(Cycle())
    override val item: StateFlow<Cycle> = _cycle.asStateFlow()

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

    private val _isTitleError = MutableStateFlow(false)
    override val isTitleError: StateFlow<Boolean> = _isTitleError.asStateFlow()

    private val _img = MutableStateFlow(_cycle.value.image)
    override val img: StateFlow<String> = _img.asStateFlow()

    private val _imgDefault = MutableStateFlow(_cycle.value.imageDefault)
    override val imgDefault: StateFlow<String> = _imgDefault

    override fun getBy(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (id == 0L) {
                /*    _cycle.update {
                        Cycle()
                    }*/
                return@launch
            }

            CycleRepo.get().getBy(id).map {
                Cycle.mapFromDbEntity(it)
            }.collect {
                updateId(it.id)
                updateTitle(it.title)
                updateComment(it.comment)
                updateStartDate(it.startDate)
                updateFinishDate(it.finishDate)
                updateImage(it.image)
                updateImageDefault(it.imageDefault)
            }
        }
    }

    override fun updateId(id: Long) {
        _id.update { id }
    }

    override fun updateImageDefault(imgStr: String) {
        _imgDefault.update {imgStr}
    }

    override fun updateEven(it: Boolean) {
        TODO("Not yet implemented")
    }

    override fun save(callback: (id: Long) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            var cS = collectToSave()
            val cE = Cycle.mapToEntity(cS)
            val save = CycleRepo.get().save(cE)

            launch(Dispatchers.Main) {
                callback(save._id)
            }
        }
    }

    override fun updateTitle(title: String) {
        _title.update { title }
    }

    override fun updateImage(imgStr: String) {
        _img.update { imgStr }
    }

    override fun updateStartDate(date: Date) {
        _startDate.update { Entity.formatDateToString(date) }
    }

    override fun updateFinishDate(date: Date) {
        _finishDate.update { Entity.formatDateToString(date) }
    }

    override fun updateComment(comment: String) {
        _comment.update { comment }
    }


    override fun collectToSave(): Cycle {
        val c = Cycle().apply {
            id = _id.value
            comment = _comment.value
            title = _title.value
            image = _img.value
            setFinishDate(_finishDate.value)
            setStartDate(_startDate.value)
        }

        return c
    }

    override fun isBlankFields(): Boolean {
        Log.d("check fielsd", "${title.value.trim().isBlank()}")
        val isBlank = title.value.trim().isBlank()
        _isTitleError.update { isBlank }

        return isBlank
    }
    override fun saveWith(parentId: Long, callback: (id: Long) -> Unit) {
        TODO("Not yet implemented")
    }
}

