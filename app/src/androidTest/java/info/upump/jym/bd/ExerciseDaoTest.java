package info.upump.jym.bd;

import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

import static org.junit.Assert.*;

/**
 * Created by explo on 08.03.2018.
 */
public class ExerciseDaoTest {
    private Exercise exercise;
    private ExerciseDao exerciseDao;

    @Before
    public void setUp() throws Exception {
        exerciseDao = new ExerciseDao(InstrumentationRegistry.getTargetContext());

    }

    @After
    public void tearDown() throws Exception {
        exerciseDao.close();
    }

    @Test
    public void getAll() throws Exception {
        exercise = new Exercise();
        exercise.setTitle("create title");
        exercise.setComment("create comment");
        exercise.setDefaultType(false);
        exercise.setTypeMuscle(TypeMuscle.ABS);
        exercise.setStartDate("2020-03-03");
        exercise.setFinishDate("2020-03-03");
        exercise.setParentId(0);
        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        List<Exercise> exerciseList = exerciseDao.getAll();
        Assert.assertTrue(exerciseList.size()>0);
    }

    @Test
    public void create() throws Exception {
        exercise = new Exercise();
        exercise.setTitle("create title");
        exercise.setComment("create comment");
        exercise.setDefaultType(false);
        exercise.setTypeMuscle(TypeMuscle.ABS);
        exercise.setStartDate("2020-03-03");
        exercise.setFinishDate("2020-03-03");
        exercise.setParentId(0);
        long create = exerciseDao.create(exercise);

        Assert.assertNotEquals(create, -1);
    }

    @Test
    public void delete() throws Exception {
        exercise = new Exercise();
        exercise.setTitle("create title");
        exercise.setComment("create comment");
        exercise.setDefaultType(false);
        exercise.setTypeMuscle(TypeMuscle.ABS);
        exercise.setStartDate("2020-03-03");
        exercise.setFinishDate("2020-03-03");
        exercise.setParentId(0);
        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        boolean delete = exerciseDao.delete(exercise);
        Assert.assertTrue(delete);

    }

    @Test
    public void update() throws Exception {
        exercise = new Exercise();
        exercise.setTitle("create title");
        exercise.setComment("create comment");
        exercise.setDefaultType(false);
        exercise.setTypeMuscle(TypeMuscle.ABS);
        exercise.setStartDate("2020-03-03");
        exercise.setFinishDate("2020-03-03");
        exercise.setParentId(0);
        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        exercise.setComment("update comment");
        boolean update = exerciseDao.update(exercise);
        Assert.assertTrue(update);
    }

}