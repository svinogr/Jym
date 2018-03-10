package info.upump.jym.bd;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import info.upump.jym.entity.Workout;

import static org.junit.Assert.*;

/**
 * Created by explo on 08.03.2018.
 */
@RunWith(AndroidJUnit4.class)
public class WorkoutDaoTest {
    private Workout workout;
    private WorkoutDao workoutDao;

    @Before
    public void setUp() throws Exception {
        workoutDao = new WorkoutDao(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        workoutDao.close();
    }

    @Test
    public void getAll() throws Exception {
        workout = new Workout();
        workout.setTitle("test title workout for delete");
        workout.setComment("workout comment");
        workout.setWeekEven(true);
        workout.setDefaultType(true);
        workout.setStartDate("2018-08-08");
        workout.setFinishDate("2019-08-08");
        long id = workoutDao.create(workout);
        workout.setId(id);
        Assert.assertNotEquals(id, -1);

        List<Workout> workoutList = workoutDao.getAll();
        Assert.assertTrue(workoutList.size()>0);

        Assert.assertTrue(workoutDao.delete(workout));
    }

    @Test
    public void create() throws Exception {
        workout = new Workout();
        workout.setTitle("test title workout");
        workout.setComment("workout comment");
        workout.setWeekEven(true);
        workout.setDefaultType(true);
        workout.setStartDate("2018-08-08");
        workout.setFinishDate("2019-08-08");
        long id = workoutDao.create(workout);
        workout.setId(id);
        Assert.assertNotEquals(id, -1);

        Assert.assertTrue(workoutDao.delete(workout));
    }

    @Test
    public void delete() throws Exception {
        workout = new Workout();
        workout.setTitle("test title workout for delete");
        workout.setComment("workout comment");
        workout.setWeekEven(true);
        workout.setDefaultType(true);
        workout.setStartDate("2018-08-08");
        workout.setFinishDate("2019-08-08");
        long id = workoutDao.create(workout);
        workout.setId(id);
        Assert.assertNotEquals(id, -1);

        boolean delete = workoutDao.delete(workout);
        Assert.assertTrue(delete);
    }

    @Test
    public void update() throws Exception {
        workout = new Workout();
        workout.setTitle("test title workout for delete");
        workout.setComment("workout comment");
        workout.setWeekEven(true);
        workout.setDefaultType(true);
        workout.setStartDate("2018-08-08");
        workout.setFinishDate("2019-08-08");
        long id = workoutDao.create(workout);
        workout.setId(id);
        Assert.assertNotEquals(id, -1);

        workout.setTitle("updated title");
        workout.setComment("update comment");
        boolean update = workoutDao.update(workout);
        Assert.assertTrue(update);

        Assert.assertTrue(workoutDao.delete(workout));

    }

    @Test
    public void getById()throws Exception{
        workout = new Workout();
        workout.setTitle("getById title");
        workout.setComment("workout comment");
        workout.setWeekEven(true);
        workout.setDefaultType(true);
        workout.setStartDate("2018-08-08");
        workout.setFinishDate("2019-08-08");
        long id = workoutDao.create(workout);
        workout.setId(id);
        Assert.assertNotEquals(id, -1);

        Workout byId = workoutDao.getById(workout.getId());
        Assert.assertEquals(workout.getTitle(),byId.getTitle());
        Assert.assertEquals(workout.getComment(),byId.getComment());
        Assert.assertEquals(workout.isWeekEven(),byId.isWeekEven());
        Assert.assertEquals(workout.isDefaultType(),byId.isDefaultType());
        Assert.assertEquals(workout.getStartStringFormatDate(),byId.getStartStringFormatDate());
        Assert.assertEquals(workout.getFinishStringFormatDate(),byId.getFinishStringFormatDate());
        Assert.assertEquals(workout.getParentId(),byId.getParentId());

        Assert.assertTrue(workoutDao.delete(workout));
    }


    @Test
    public void getByParentId()throws Exception{
        workout = new Workout();
        workout.setTitle("test title workout for delete");
        workout.setComment("workout comment");
        workout.setWeekEven(true);
        workout.setDefaultType(true);
        workout.setStartDate("2018-08-08");
        workout.setFinishDate("2019-08-08");
        long parentId =154;
        workout.setParentId(parentId);
        long id = workoutDao.create(workout);
        workout.setId(id);
        Assert.assertNotEquals(id, -1);

        List<Workout> setsList = workoutDao.getByParentId(parentId);
        Assert.assertTrue(setsList.size()>0);

        Assert.assertTrue(workoutDao.delete(workout));
    }
}