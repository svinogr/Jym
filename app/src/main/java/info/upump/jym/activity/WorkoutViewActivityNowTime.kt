//package info.upump.jym.activity
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.os.PersistableBundle
//import android.util.Log
//import android.util.TypedValue
//import android.view.*
//import android.widget.Button
//import android.widget.LinearLayout
//import android.widget.PopupWindow
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.android.material.snackbar.Snackbar
//import info.upump.jym.R
//import info.upump.jym.activity.constant.Constants
//import info.upump.jym.adapters.SetAdapterForNowTime
//import info.upump.jym.bd.ExerciseDao
//import info.upump.jym.bd.SetDao
//import info.upump.jym.bd.WorkoutDao
//import info.upump.jym.databinding.ActivityWorkoutViewBinding
//import info.upump.jym.entity.Sets
//import info.upump.jym.entity.Workout
//import kotlinx.coroutines.*
//
//class WorkoutViewActivity : AppCompatActivity() {
//    private val TAG = "WorkoutViewActivity"
//    private var isStart = false
//    private lateinit var workout: Workout
//    private lateinit var binding: ActivityWorkoutViewBinding
//    private lateinit var startBtn: Button
//    private lateinit var stopBtn: Button
//    private lateinit var secondsTextView: TextView
//    private lateinit var minutesTextView: TextView
//    private lateinit var hoursTextView: TextView
//    private lateinit var recyclerExercise: RecyclerView
//    private val limitUpTime = 59
//    private var jobSeconds: Job? = null
//    private var seconds = 0
//    private var minutes = 0
//    private var hours = 0
//    private val secondsState = "second"
//    private val minuteState = "minutes"
//    private val hoursState = "hours"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //  setContentView(R.layout.activity_workout_view)
//
//        binding = ActivityWorkoutViewBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val workoutId = intent.extras?.getLong(WorkoutViewActivity.ID)
//        val workoutDao = WorkoutDao.getInstance(this, null)
//
//        workout = workoutDao.getById(workoutId!!)
//
//        settingsActionBar()
//
//        startBtn = binding.workoutNowStartBut
//        stopBtn = binding.workoutNowStopBut
//
//        secondsTextView = binding.workoutNowSecond
//        minutesTextView = binding.workoutNowMinute
//        hoursTextView = binding.workoutNowHour
//
//        recyclerExercise = binding.workoutNowRecExecises
//
//        setBtnListener()
//        setWorkoutView()
//    }
//
//    private fun settingsActionBar() {
//        val toolBar = binding.workoutNowToolbar
//        toolBar.title = workout.title
//        setSupportActionBar(toolBar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        supportActionBar?.setBackgroundDrawable(ColorDrawable(getResources().getColor(workout.day.color)))
//    }
//
//    private fun setWorkoutView() {
//        val exerciseDao = ExerciseDao.getInstance(this, null)
//        val listExercises = exerciseDao.getByParentId(workout.id)
//        Log.d("Title", listExercises.size.toString())
//        val listSets = mutableListOf<Sets>()
//        val linearLayoutManager = LinearLayoutManager(this)
//        recyclerExercise.setLayoutManager(linearLayoutManager)
//
//        val setDao = SetDao.getInstance(this, null)
//
//        listExercises.forEach {
//            val setFN = Sets()
//            setFN.title = it.exerciseDescription.title
//            setFN.id = -1
//            listSets.add(setFN)
//            val sets = setDao.getByParentId(it.id)
//            sets.forEach { set ->
//                listSets.add(set)
//            }
//        }
//
//        val adapter = SetAdapterForNowTime(
//            listSets,
//            Constants.USER_TYPE,
//            null
//        )
//
//        recyclerExercise.adapter = adapter
//    }
//
//
//    private fun setBtnListener() {
//        startBtn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                if (!isStart) {
//                    isStart = true
//                    startStopWatch()
//                } else {
//                    isStart = false
//                    pauseStopWatch()
//                }
//            }
//        })
//
//        stopBtn.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                stopStopWatch()
//            }
//        })
//    }
//
//    private fun stopStopWatch() {
//        jobSeconds?.cancel()
//
//        seconds = 0
//        minutes = 0
//        hours = 0
//
//        isStart = false
//        stopBtn.visibility = View.GONE
//        secondsTextView.text = "00"
//        startBtn.text = "START"
//    }
//
//    private fun pauseStopWatch() {
//        jobSeconds?.cancel()
//        startBtn.text = getString(R.string.btn_start_stopwatch_title)
//    }
//
//    private fun startStopWatch() {
//        stopBtn.visibility = View.VISIBLE
//        startBtn.text = getString(R.string.btn_stop_stopwatch_title)
//
//        jobSeconds?.cancel()
//
//        jobSeconds = GlobalScope.launch(Dispatchers.Main) {
//            while (true) {
//                seconds += 1
//                if (seconds > limitUpTime) {
//                    seconds = 0
//                    minutes += 1
//                }
//
//                if (minutes > limitUpTime) {
//                    seconds = 0
//                    minutes = 0
//                    hours += 1
//                }
//
//                var secText = seconds.toString()
//                var minText = minutes.toString()
//                var hourText = hours.toString()
//
//                if (seconds < 10) {
//                    secText = "0${secText}"
//                }
//
//                if (minutes < 10) {
//                    minText = "0${minText}"
//                }
//
//                if (hours < 10) {
//                    hourText = "0${hourText}"
//                }
//
//                secondsTextView.text = secText
//                minutesTextView.text = minText
//                hoursTextView.text = hourText
//
//                delay(1000)
//            }
//        }
//    }
//
//    companion object {
//        const val ID = "id"
//
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(id: Long, context: Context) =
//            Intent(context, WorkoutViewActivity::class.java).apply {
//                putExtra(WorkoutViewActivity.ID, id)
//            }
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        seconds = savedInstanceState.getInt(secondsState, 0)
//        minutes = savedInstanceState.getInt(minuteState, 0)
//        hours = savedInstanceState.getInt(hoursState, 0)
//
//        if (seconds != 0 || minutes != 0 || hours != 0) {
//            startStopWatch()
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        println("saves")
//        Log.d(TAG, "save")
//        Log.d(TAG, seconds.toString())
//        outState.putInt(secondsState, seconds)
//        outState.putInt(minuteState, minutes)
//        outState.putInt(
//            hoursState, hours
//        )
//        super.onSaveInstanceState(outState)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        jobSeconds?.cancel()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.workout_now_time_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                exit()
//                return true
//            }
//            R.id.action_comments -> {
//                commentPopUp()
//                return true
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onBackPressed() {
//        exit()
//    }
//
//    private fun exit() {
//        Snackbar.make(
//            findViewById(R.id.orkout_now_time_coord_layout),
//            R.string.snack_exit_workout,
//            Snackbar.LENGTH_LONG
//        )
//            .setAction(R.string.yes) { finish() }.show()
//    }
//
//    private fun commentPopUp() {
//        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val viewPopUp = inflater.inflate(R.layout.popup_now_time_activity2, null)
//        val text = viewPopUp.findViewById<TextView>(R.id.popup_now_activity_text)
//        val height = LinearLayout.LayoutParams.MATCH_PARENT
//        val wight = LinearLayout.LayoutParams.MATCH_PARENT
//        val focusable = true
//        text.text = workout.comment
//
//        val popUp = PopupWindow(viewPopUp, height, wight, focusable)
//        popUp.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
//
//        popUp.elevation = 20F
//
//        viewPopUp.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                popUp.dismiss()
//                return true
//            }
//        })
//    }
//}
//
//fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
//    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
//).toInt()