package info.upump.jym.kotlinClasses.backupDb

import android.net.Uri
import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription

interface DBReadable {
    fun readFrom()
    fun readFrom(uri: Uri): Pair<List<Cycle>, Map<ExerciseDescription, List<Exercise>>>?
}