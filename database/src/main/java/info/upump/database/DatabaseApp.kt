package info.upump.database


import android.content.Context
import android.util.Log
import androidx.room.Room
import info.upump.jym.db.RoomDB
import info.upump.jym.db.repo.CycleRepo
import info.upump.jym.db.repo.ExerciseDescriptionRepo
import info.upump.jym.db.repo.ExerciseRepo
import info.upump.jym.db.repo.SetsRepo
import info.upump.jym.db.repo.WorkoutRepo

import java.io.File

class DatabaseApp (val context: Context) {

    companion object {
        lateinit var db: RoomDB

        fun initilizeDb(context: Context) {
            val file = File(RoomDB.DB_PATH)

            if (!file.exists()) {
                Log.d("DatabaseApp", "file isnt exist")

                db =
                    Room.databaseBuilder(context, RoomDB::class.java, RoomDB.BASE_NAME)
                        .createFromAsset(RoomDB.BASE_NAME)
                        .build()
            } else {
                db =
                    Room.databaseBuilder(context, RoomDB::class.java, RoomDB.BASE_NAME)
                        .build()
            }


            WorkoutRepo.initialize(context, db)
            CycleRepo.initialize(context, db)
            ExerciseRepo.initialize(context, db)
            ExerciseDescriptionRepo.initialize(context, db)
            SetsRepo.initialize(context, db)

        }
    }
}

/*
        private fun initializeDb(context: Context) : RoomDB {
            val file = File(RoomDB.DB_PATH)
            var db: RoomDB? = null

            if (!file.exists()) {
                Log.d("DatabaseApp", "file isnt exist")

                db =
                    Room.databaseBuilder(context, RoomDB::class.java, RoomDB.BASE_NAME)
                        .createFromAsset(RoomDB.BASE_NAME)
                        .build()
            } else {
                db =
                    Room.databaseBuilder(context, RoomDB::class.java, RoomDB.BASE_NAME)
                        .build()
            }
            return db!!

        }



    }

     init{
        Log.d("initialize db", "initialize db")
        initializeDb()
        WorkoutRepo.initialize(context, db)
        CycleRepo.initialize(context,db)
        ExerciseRepo.initialize(context, db)
        ExerciseDescriptionRepo.initialize(context, db)
        SetsRepo.initialize(context,db)
    }
*/


//}