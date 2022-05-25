package info.upump.jym.activity

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.upump.jym.activity.constant.Constants
import info.upump.jym.adapters.SetAdapterForNowTime
import info.upump.jym.bd.ExerciseDao
import info.upump.jym.bd.SetDao
import info.upump.jym.bd.WorkoutDao
import info.upump.jym.databinding.ActivityWorkoutViewBinding
import info.upump.jym.entity.Sets
import info.upump.jym.entity.Workout
import kotlinx.coroutines.*

class WorkoutViewActivity : AppCompatActivity() {
    private var isStart = false
    private lateinit var workout: Workout
    private lateinit var binding: ActivityWorkoutViewBinding
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var secondsTextView: TextView
    private lateinit var minutesTextView: TextView
    private lateinit var hoursTextView: TextView
    private lateinit var recyclerExercise: RecyclerView
    private val limitUpTime = 59
    private var jobSeconds: Job? = null
    private var seconds = 0
    private var minutes = 0
    private var hours = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_workout_view)

        binding = ActivityWorkoutViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutId = intent.extras?.getLong(WorkoutViewActivity.ID)
        val workoutDao = WorkoutDao.getInstance(this, null)

        workout = workoutDao.getById(workoutId!!)

        setyingsActionBar()

        startBtn = binding.workoutNowStartBut
        stopBtn = binding.workoutNowStopBut

        secondsTextView = binding.workoutNowSecond
        minutesTextView = binding.workoutNowMinute
        hoursTextView = binding.workoutNowHour

        recyclerExercise = binding.workoutNowRecExecises

        setBtnListener()
        setWorkoutView()
    }

    private fun setyingsActionBar() {
        setSupportActionBar(binding.workoutNowToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = workout.title
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getResources().getColor(workout.day.color)))
    }

    private fun setWorkoutView() {
        val exerciseDao = ExerciseDao.getInstance(this, null)
        val listExercises = exerciseDao.getByParentId(workout.id)
        Log.d("Title", listExercises.size.toString())
        val listSets = mutableListOf<Sets>()
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerExercise.setLayoutManager(linearLayoutManager)

        val setDao = SetDao.getInstance(this, null)

        listExercises.forEach {
            val setFN = Sets()
            setFN.title = it.exerciseDescription.title
            setFN.id = -1
            listSets.add(setFN)
            val sets = setDao.getByParentId(it.id)
            sets.forEach { set ->
                listSets.add(set)
            }
        }


        val adapter = SetAdapterForNowTime(
            listSets,
            Constants.USER_TYPE,
            null
        )

        recyclerExercise.adapter = adapter
    }


    private fun setBtnListener() {
        startBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (!isStart) {
                    isStart = true
                    startStopWatch()
                } else {
                    isStart = false
                    pauseStopWatch()
                }
            }
        })


        stopBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                stopStopWatch()
            }
        })
    }

    private fun stopStopWatch() {
        jobSeconds?.cancel()

        seconds = 0
        minutes = 0
        hours = 0

        isStart = false
        stopBtn.visibility = View.GONE
        secondsTextView.text = "00"
        startBtn.text = "START"
    }

    private fun pauseStopWatch() {
        jobSeconds?.cancel()
        startBtn.text = "START"
    }

    private fun startStopWatch() {
        stopBtn.visibility = View.VISIBLE
        startBtn.text = "PAUSE"

        jobSeconds?.cancel()

        jobSeconds = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                seconds += 1
                Log.d("TAG", seconds.toString())
                if (seconds >= limitUpTime) {
                    seconds = 0
                    minutes += 1
                }

                if (minutes >= limitUpTime) {
                    seconds = 0
                    minutes = 0
                    hours += 1
                }

                var secText = seconds.toString()
                var minText = minutes.toString()
                var hourText = hours.toString()

                if (seconds < 10) {
                    secText = "0${secText}"
                }

                if (minutes < 10) {
                    minText = "0${minText}"
                }

                if (hours < 10) {
                    hourText = "0${hourText}"
                }

                secondsTextView.text = secText

                delay(1000)
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        jobSeconds?.cancel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()