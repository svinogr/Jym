package info.upump.jym.bd;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import info.upump.jym.entity.ExerciseDescription;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class ExerciseDescriptionDaoTest {
    private ExerciseDescription exerciseDescription;
    private ExerciseDescriptionDao exerciseDescriptionDao;

    @Before
    public void setUp() throws Exception {
        exerciseDescriptionDao = new ExerciseDescriptionDao(InstrumentationRegistry.getTargetContext());
        exerciseDescription = new ExerciseDescription();
        exerciseDescription.setDescription("bla");
        exerciseDescription.setParentId(123);
    }

    @After
    public void tearDown() throws Exception {
        exerciseDescriptionDao.close();
    }

    @Test
    public void create() throws Exception {
        long id = exerciseDescriptionDao.create(exerciseDescription);
        exerciseDescription.setId(id);
        Assert.assertNotEquals(id, -1);

        Assert.assertTrue(exerciseDescriptionDao.delete(exerciseDescription));
    }

    @Test
    public void delete() throws Exception {
        long id = exerciseDescriptionDao.create(exerciseDescription);
        exerciseDescription.setId(id);
        Assert.assertNotEquals(id, -1);

        Assert.assertTrue(exerciseDescriptionDao.delete(exerciseDescription));
    }

    @Test
    public void update() throws Exception {
        long id = exerciseDescriptionDao.create(exerciseDescription);
        exerciseDescription.setId(id);
        Assert.assertNotEquals(id, -1);

        exerciseDescription.setDescription("bla bla");
        Assert.assertTrue(exerciseDescriptionDao.update(exerciseDescription));

        Assert.assertTrue(exerciseDescriptionDao.delete(exerciseDescription));
    }

    @Test
    public void getById() throws Exception {
        long id = exerciseDescriptionDao.create(exerciseDescription);
        exerciseDescription.setId(id);
        Assert.assertNotEquals(id, -1);

        ExerciseDescription byId = exerciseDescriptionDao.getById(id);

        Assert.assertEquals(exerciseDescription.getId(), byId.getId());
        Assert.assertEquals(exerciseDescription.getDescription(), byId.getDescription());
        Assert.assertEquals(exerciseDescription.getParentId(), byId.getParentId());

        Assert.assertTrue(exerciseDescriptionDao.delete(exerciseDescription));

    }

    @Test
    public void getByParentId() throws Exception {
        long id = exerciseDescriptionDao.create(exerciseDescription);
        exerciseDescription.setId(id);
        Assert.assertNotEquals(id, -1);

        List<ExerciseDescription> byParentId = exerciseDescriptionDao.getByParentId(exerciseDescription.getParentId());
        Assert.assertTrue(byParentId.size()>0);

        Assert.assertTrue(exerciseDescriptionDao.delete(exerciseDescription));

    }

}