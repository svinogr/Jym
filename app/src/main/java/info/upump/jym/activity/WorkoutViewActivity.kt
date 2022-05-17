package info.upump.jym.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import info.upump.jym.R
import info.upump.jym.bd.WorkoutDao
import info.upump.jym.entity.Workout

class WorkoutViewActivity : AppCompatActivity() {
    private lateinit var workout: Workout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_view)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val workoutId = intent.extras?.getLong(WorkoutViewActivity.ID)
        val workoutDao = WorkoutDao.getInstance(this, null)
     /*   Log.d("TAG", "WorkoutViewActivity sonCraete ${workoutId}")
        Log.d("TAG", "WorkoutViewActivity sonCraete ${workoutDao}")*/
        workout = workoutDao.getById(workoutId!!)
       // Log.d("TAG", "WorkoutViewActivity sonCraete ${byId}")
        this.title = workout.title

    }


    companion object {
        const val ID = "id"

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Long, context: Context) =
            Intent(context, WorkoutViewActivity::class.java).apply {
              putExtra(WorkoutViewActivity.ID, id)
            }
    }
}
