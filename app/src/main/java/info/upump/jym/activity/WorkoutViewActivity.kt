package info.upump.jym.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import info.upump.jym.bd.WorkoutDao
import info.upump.jym.databinding.ActivityWorkoutViewBinding
import info.upump.jym.entity.Workout
import kotlinx.coroutines.*
import kotlin.math.log

class WorkoutViewActivity : AppCompatActivity() {
    private var isStart = false
    private var isPause = false
    private lateinit var workout: Workout
    private lateinit var binding: ActivityWorkoutViewBinding
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var secondsTextView: TextView
    private lateinit var minutesTextView: TextView
    private lateinit var hoursTextView: TextView
    private val limitUpTime = 59
    private var jobSeconds: Job? = null
    private var seconds = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_workout_view)
        binding = ActivityWorkoutViewBinding.inflate(layoutInflater)

        setContentView(binding.root)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val workoutId = intent.extras?.getLong(WorkoutViewActivity.ID)
        val workoutDao = WorkoutDao.getInstance(this, null)

        workout = workoutDao.getById(workoutId!!)
        this.title = workout.title

        startBtn = binding.workoutNowStartBut
        stopBtn = binding.workoutNowStopBut

        secondsTextView = binding.workoutNowSecond
        minutesTextView = binding.workoutNowMinute
        hoursTextView = binding.workoutNowHour

        setBtnListener()

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
                var secText = seconds.toString()
                if (seconds < 10) {
                    secText = "0${secText}"
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
}
