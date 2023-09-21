package info.upump.database.repo

import android.os.SystemClock
import android.util.Log
import androidx.room.Transaction
import info.upump.database.RepoActionsSpecific
import info.upump.database.RoomDB
import info.upump.database.entities.CycleEntity
import info.upump.database.entities.CycleFullEntity
import info.upump.database.entities.CycleFullEntityWithWorkouts
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class CycleRepo private constructor(db: RoomDB) :
    RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> {
    private val cycleDao = db.cycleDao()
    private val workoutRepo = WorkoutRepo.get()

    companion object {
        private var instance: CycleRepo? = null

        fun initialize(db: RoomDB) {
            instance = CycleRepo(db)
        }

        fun get(): RepoActionsSpecific<CycleEntity, CycleFullEntityWithWorkouts> {
            return instance ?: throw IllegalStateException("first need initialize repo")
        }
    }

    override fun getFullEntityBy(id: Long): Flow<CycleFullEntityWithWorkouts> {
        return cycleDao.getFullBy(id)
    }

    override fun getAllFullEntity(): Flow<List<CycleFullEntityWithWorkouts>> {
        TODO("Not yet implemented")
    }

    override fun getAllFullEntityByParent(id: Long): Flow<List<CycleFullEntityWithWorkouts>> {
        TODO("Not yet implemented")
    }


    override fun getAllFullEntityTemplate(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllTemplate()
    }

    override fun save(item: CycleEntity): CycleEntity {
        Log.d("save", "id = ${item._id}")
        if (item._id == 0L) {
            val newId = cycleDao.save(item)
            item.apply { _id = newId }
        } else cycleDao.update(item)

        return item
    }

    override fun getAllFullEntityPersonal(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllPersonalCycles()
    }

    fun getAllFullestEntityPersonal(): Flow<List<CycleFullEntity>> {
        return cycleDao.getAllFullPersonalCycles()
    }

    override fun getAllFullEntityDefault(): Flow<List<CycleFullEntityWithWorkouts>> {
        return cycleDao.getAllTemplate()
    }

    @Transaction
    override fun delete(id: Long) {
        cycleDao.delete(id)
        workoutRepo.deleteByParent(id)

    }

    override fun deleteByParent(parentId: Long) {
        TODO("Not yet implemented")
    }

    override fun deleteChilds(parentId: Long) {
        Log.d("clean", "$parentId")
        workoutRepo.deleteByParent(parentId)
    }

    override fun update(item: CycleEntity): CycleEntity {
        cycleDao.update(item)
        return item
    }

    suspend fun saveFullEntitiesOnlyFromOtherDB(list: List<CycleFullEntity>) {
        val s = System.currentTimeMillis()
        val listForDbWrite = mutableListOf<Deferred<CycleEntity>>()
        val workoutsRepo = WorkoutRepo.get()
        coroutineScope {

            for (cycle in list) {
                val cycleE =
                    async {
                        val c = cycle.cycleEntity
                        c._id = 0
                        val id = cycleDao.save(c)

                        for (workout in cycle.listWorkoutEntity) {
                            val repoW = workoutsRepo

                            async {
                                workout.workoutEntity._id = 0
                                workout.workoutEntity.parent_id = id
                                val idW = repoW.save(workout.workoutEntity)._id

                                val repoE = ExerciseRepo.get()

                                for (exercise in workout.listExerciseEntity) {
                                    async {
                                        exercise.exerciseEntity._id = 0
                                        exercise.exerciseEntity.parent_id = idW
                                        val idE = repoE.save(exercise.exerciseEntity)

                                        val repoS = SetsRepo.get()

                                        for (sets in exercise.listSetsEntity) {
                                            async {
                                                sets._id = 0
                                                sets.parent_id = idE._id
                                                repoS.save(sets)

                                            }
                                        }
                                    }
                                }

                            }
                        }


                        return@async c
                    }
                listForDbWrite.add(cycleE)
            }
        }

        val f = System.currentTimeMillis()
        listForDbWrite.awaitAll()
        println(f - s)
    }


    private suspend fun prepareForWriteInDb(list: List<CycleFullEntity>): List<CycleFullEntity> {
        val listForDbWrite = mutableListOf<Deferred<CycleFullEntity>>()
        coroutineScope {

            for (cycle in list) {
                //    Log.d("prepareForWriteInDb", "-cycle")

                val cycleA = async {
                    cycle.cycleEntity._id = 0

                    for (workout in cycle.listWorkoutEntity) {
                        //Log.d("prepareForWriteInDb", "--workout")

                        async {
                            workout.workoutEntity._id = 0

                            for (exercise in workout.listExerciseEntity) {
                                //   Log.d("prepareForWriteInDb", "---esercise")

                                async {

                                    exercise.exerciseEntity._id = 0

                                    for (set in exercise.listSetsEntity) {
                                        Log.d("prepareForWriteInDb", "----set")
                                        async {
                                            set._id = 0
                                        }
                                    }

                                }
                            }
                        }
                    }
                    return@async cycle
                }
                listForDbWrite.add(cycleA)
            }


        }

        /*
                val o = listForDbWrite.awaitAll()
                println(o.size.toString())*/

        return listForDbWrite.awaitAll()
    }
}