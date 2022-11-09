package info.upump.jym.activity.exercise

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.upump.jym.activity.constant.Constants.*
import info.upump.jym.bd.ExerciseDao
import info.upump.jym.bd.SetDao
import info.upump.jym.entity.Sets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ExerciseVM : ViewModel() {
    private var _sets = MutableLiveData<MutableList<Sets>>()
    val set: LiveData<MutableList<Sets>> = _sets

    private var _idItem =  MutableLiveData<Int>()
    val idItem = _idItem

    fun getSetsByExerciseId(id: Long, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val setsDao = SetDao.getInstance(context, null)
            val sets = setsDao.getByParentId(id)

            _sets.postValue(sets)
        }
    }

    fun deleteSets(context: Context, exId: Long ) {
        viewModelScope.launch(Dispatchers.IO) {
            val exerciseDao = ExerciseDao.getInstance(context, null)
            val isClear = exerciseDao.clear(exId)
            if (isClear) _sets.postValue(mutableListOf())
        }
    }

    fun deleteOneSets(context: Context, sId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val setsDao = SetDao.getInstance(context, null)
            val setForDel = Sets()
            setForDel.id = sId
            setsDao.delete(setForDel)

            val listSets = _sets.value

            if (listSets == null) {
                return@launch
            }

            for (i in 0 until listSets.size step 1) {
                if (listSets[i].id == sId) {
                    listSets.removeAt(i)
                    _idItem.postValue(i)
                    break
                }
            }




        }

    }

    /* Thread thread = new Thread(new Runnable() {
             @Override
             public void run() {
                 int q = data.getIntExtra(QUANTITY, 1);
                 Sets sets = null;
                 List<Sets> setsList = new ArrayList<>();
                 SetDao setDao = SetDao.getInstance(getApplicationContext(), null);
                 for (int i = 0; i < q; i++) {
                     sets = new Sets();
                     sets.setStartDate(new Date());
                     sets.setFinishDate(new Date());
                     sets.setWeight(data.getDoubleExtra(WEIGHT, 0));
                     sets.setWeightPast(data.getDoubleExtra(PAST_WEIGHT, 0));
                     sets.setReps(data.getIntExtra(REPS, 0));
                     sets.setParentId(exercise.getId());
                     long l = setDao.create(sets);
                     sets.setId(l);
                     setsList.add(sets);
                 }

                 if (setsList.size() != 0) {
                     handler.sendMessageDelayed(handler.obtainMessage(CREATE, setsList), 0);
                 }
             }
         });
         thread.start();*/
    fun addNewSets(data: Intent, context: Context, exId: Long) {
        println(" exid $exId")
        viewModelScope.launch(Dispatchers.IO) {
            val q = data.getIntExtra(QUANTITY, 1)
            val setsDao = SetDao.getInstance(context, null)
            var listSets = _sets.value
            for (i in 0 until q step 1) {
                val sets = Sets()
                sets.startDate = Date()
                sets.finishDate = Date()
                sets.weight = data.getDoubleExtra(WEIGHT, 0.0)
                sets.weightPast = data.getDoubleExtra(PAST_WEIGHT, 0.0)
                sets.reps = data.getIntExtra(REPS, 0)
                sets.parentId = exId
                setsDao.create(sets)

                if (listSets == null) {
                    listSets = mutableListOf()
                }

                listSets.add(sets)
            }

            _sets.postValue(listSets!!)
        }
    }
}