package info.upump.jym.kotlinClasses.backupDb

import info.upump.jym.entity.Cycle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.ExerciseDescription

interface DBReadable {
    fun readFrom(): Pair<List<Cycle>, Map<ExerciseDescription, List<Exercise>>>?
    fun readFromFile(fileName: String)
}