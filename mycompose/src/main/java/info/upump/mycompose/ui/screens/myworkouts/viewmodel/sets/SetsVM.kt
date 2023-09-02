package info.upump.mycompose.ui.screens.myworkouts.viewmodel.sets

import android.util.Log
import androidx.lifecycle.viewModelScope
import info.upump.database.repo.SetsRepo
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.BaseVMWithStateLoad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetsVM : BaseVMWithStateLoad(), SetsVMInterface {
    private val _sets = MutableStateFlow<Sets>(Sets())
    override val item: StateFlow<Sets> = _sets.asStateFlow()

    private val _id = MutableStateFlow(0L)
    override val id: StateFlow<Long> = _id.asStateFlow()

    private val _parentId = MutableStateFlow(0L)
    override val parentId: StateFlow<Long> = _parentId.asStateFlow()

    private val _weight = MutableStateFlow(0.0)
    override val weight: StateFlow<Double> = _weight.asStateFlow()

    private val _reps = MutableStateFlow(0)
    override val reps: StateFlow<Int> = _reps.asStateFlow()

    private val _weightPast = MutableStateFlow(0.0)
    override val weightPast: StateFlow<Double> = _weightPast.asStateFlow()

    private val _quantity = MutableStateFlow(0)
    override val quantity: StateFlow<Int> = _quantity.asStateFlow()
    override fun updateReps(reps: Int) {
        _reps.update { reps }
    }

    override fun updateWeight(weight: Double) {
        _weightPast.update { _weight.value }
        _weight.update { weight }
    }

    override fun updatePastWeight(weight: Double) {
        _weightPast.update { weight }
    }

    override fun updateQuantity(quantity: Int) {
        _quantity.update { quantity }
    }

    override fun updateParentId(parentId: Long) {
        _parentId.update { parentId }
    }

    override fun getBy(id: Long) {
        _stateLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val repo = SetsRepo.get()
            repo.getBy(id).map { entity ->
                Sets.mapFromDbEntity(entity)

            }.collect() { sets ->
                _sets.update { sets }
                _id.update { sets.id }
                _weight.update { sets.weight }
                _reps.update { sets.reps }
                _weightPast.update { sets.weightPast }
            }

            _stateLoading.value = false
        }
    }

    override fun save() {
        if(quantity.value == 0) return
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("save set", "${weight.value} ${reps.value} ${quantity.value}")
            val sets = Sets().apply {
                id = 0
                parentId = this@SetsVM.parentId.value
                reps = this@SetsVM.reps.value
                weight = this@SetsVM.weight.value
                weightPast = this@SetsVM._weightPast.value
            }
            val setsRepo = SetsRepo.get()

            for (i in 1..quantity.value) {
                setsRepo.save(Sets.mapToEntity(sets))
            }
        }
    }

    companion object {
        val vmOnlyForPreview by lazy {
            object : SetsVMInterface {
                private val _sets = MutableStateFlow<Sets>(Sets())
                override val item: StateFlow<Sets> = _sets.asStateFlow()

                private val _id = MutableStateFlow(0L)
                override val id: StateFlow<Long> = _id.asStateFlow()

                private val _parentId = MutableStateFlow(0L)
                override val parentId: StateFlow<Long> = _parentId.asStateFlow()

                private val _weight = MutableStateFlow(0.0)
                override val weight: StateFlow<Double> = _weight.asStateFlow()

                private val _reps = MutableStateFlow(0)
                override val reps: StateFlow<Int> = _reps.asStateFlow()

                private val _weightPast = MutableStateFlow(0.0)
                override val weightPast: StateFlow<Double> = _weightPast.asStateFlow()

                private val _quantity = MutableStateFlow(0)
                override val quantity: StateFlow<Int> = _quantity.asStateFlow()

                override fun updateReps(reps: Int) {
                    TODO("Not yet implemented")
                }

                override fun updateWeight(weight: Double) {
                    TODO("Not yet implemented")
                }

                override fun updatePastWeight(weight: Double) {
                    TODO("Not yet implemented")
                }

                override fun updateQuantity(quantity: Int) {
                    TODO("Not yet implemented")
                }

                override fun updateParentId(parentId: Long) {
                    TODO("Not yet implemented")
                }

                override fun getBy(id: Long) {
                    TODO("Not yet implemented")
                }

                override fun save() {
                    TODO("Not yet implemented")
                }

            }
        }
    }

}
