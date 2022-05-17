/*
package info.upump.jym.bd;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

*/
/**
 * Created by explo on 08.03.2018.
 *//*

@RunWith(AndroidJUnit4.class)
public class ExerciseDaoTest {
    private Exercise exercise;
    private ExerciseDao exerciseDao;

    @Before
    public void setUp() throws Exception {
        exerciseDao = new ExerciseDao(InstrumentationRegistry.getTargetContext());
        exercise = new Exercise();
        exercise.setTitle("create title");
        exercise.setComment("create comment");
        exercise.setDefaultType(true);
        exercise.setTemplate(true);
        //exercise.setDescription("description");
        exercise.setTypeMuscle(TypeMuscle.ABS);
        exercise.setStartDate("2020-03-03");
        exercise.setFinishDate("2020-03-03");
        exercise.setParentId(0);

    }

    @After
    public void tearDown() throws Exception {
        exerciseDao.close();
    }

    @Test
    public void getAll() throws Exception {

        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        List<Exercise> exerciseList = exerciseDao.getAll();
        Assert.assertTrue(exerciseList.size()>0);

        Assert.assertTrue(exerciseDao.delete(exercise));
    }

    @Test
    public void create() throws Exception {

        long create = exerciseDao.create(exercise);
        exercise.setId(create);

        Assert.assertNotEquals(create, -1);

   //     Assert.assertTrue(exerciseDao.delete(exercise));
    }

    @Test
    public void delete() throws Exception {

        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        boolean delete = exerciseDao.delete(exercise);
        Assert.assertTrue(delete);
    }

    @Test
    public void update() throws Exception {

        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        exercise.setComment("update comment");
        boolean update = exerciseDao.update(exercise);
        Assert.assertTrue(update);

        Assert.assertTrue(exerciseDao.delete(exercise));
    }

    @Test
    public void getById()throws Exception{

        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        Exercise byId = exerciseDao.getById(exercise.getId());
        Assert.assertEquals(exercise.getTitle(),byId.getTitle());
        Assert.assertEquals(exercise.getComment(),byId.getComment());
        Assert.assertEquals(exercise.isDefaultType(),byId.isDefaultType());
        Assert.assertEquals(exercise.getTypeMuscle(),byId.getTypeMuscle());
        Assert.assertEquals(exercise.getStartStringFormatDate(),byId.getStartStringFormatDate());
        Assert.assertEquals(exercise.getFinishStringFormatDate(),byId.getFinishStringFormatDate());
        Assert.assertEquals(exercise.getParentId(),byId.getParentId());

        Assert.assertTrue(exerciseDao.delete(exercise));
    }

    @Test
    public void getByParentId()throws Exception{

        long parentId = 216;
        exercise.setParentId(parentId);
        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        List<Exercise> exerciseList = exerciseDao.getByParentId(parentId);
        Assert.assertTrue(exerciseList.size()>0);

        Assert.assertTrue(exerciseDao.delete(exercise));
    }

    @Test
    public void getAllByTypeMuscle(){
        TypeMuscle typeMuscle = TypeMuscle.ABS;

        long parentId = 216;
        exercise.setParentId(parentId);
        long create = exerciseDao.create(exercise);
        exercise.setId(create);
        Assert.assertNotEquals(create, -1);

        List<Exercise> allByTypeMuscle = exerciseDao.getAllByTypeMuscle(typeMuscle);
        Assert.assertTrue(allByTypeMuscle.size()>0);
        System.out.println(allByTypeMuscle.toString());

        Assert.assertTrue(exerciseDao.delete(exercise));
    }

}*/
