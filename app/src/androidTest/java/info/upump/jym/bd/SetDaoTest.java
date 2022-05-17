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

import info.upump.jym.entity.Sets;

*/
/**
 * Created by explo on 08.03.2018.
 *//*

@RunWith(AndroidJUnit4.class)
public class SetDaoTest {
    private Sets set;
    private SetDao setDao;
    @Before
    public void setUp() throws Exception {
        setDao = new SetDao(InstrumentationRegistry.getTargetContext());

    }

    @After
    public void tearDown() throws Exception {
        setDao.close();
    }

    @Test
    public void getById() throws Exception{
        set = new Sets();
        set.setComment("create comment");
        set.setWeight(0.01);
        set.setReps(666);
        set.setStartDate("2022-03-03");
        set.setFinishDate("2022-03-03");
        set.setParentId(1);
        long create = setDao.create(set);
        set.setId(create);
        Assert.assertNotEquals(create,-1);

        Sets byId = setDao.getById(set.getId());
        Assert.assertEquals(set.getComment(),byId.getComment());
        Assert.assertEquals(set.getWeight(),byId.getWeight(),0);
        Assert.assertEquals(set.getReps(),byId.getReps());
        Assert.assertEquals(set.getStartStringFormatDate(),byId.getStartStringFormatDate());
        Assert.assertEquals(set.getFinishStringFormatDate(),byId.getFinishStringFormatDate());
        Assert.assertEquals(set.getParentId(),byId.getParentId());

        Assert.assertTrue(setDao.delete(set));

    }

    @Test
    public void getAll() throws Exception {
        set = new Sets();
        set.setComment("create comment");
        set.setWeight(0.00);
        set.setReps(333);
        set.setStartDate("2021-03-03");
        set.setFinishDate("2021-03-03");
        set.setParentId(226);
        long create = setDao.create(set);
        set.setId(create);
        Assert.assertNotEquals(create,-1);

        List<Sets> setsList = setDao.getAll();
        Assert.assertTrue(setsList.size()>0);

        Assert.assertTrue(setDao.delete(set));

    }

    @Test
    public void create() throws Exception {
        set = new Sets();
        set.setComment("create comment");
        set.setWeight(0.25);
        set.setReps(333);
        set.setStartDate("2021-03-03");
        set.setFinishDate("2021-03-03");
        set.setParentId(226);
        long create = setDao.create(set);
        set.setId(create);
        Assert.assertNotEquals(create,-1);

        Assert.assertTrue(setDao.delete(set));
    }

    @Test
    public void delete() throws Exception {
        set = new Sets();
        set.setComment("create comment");
        set.setWeight(0.25);
        set.setReps(333);
        set.setStartDate("2021-03-03");
        set.setFinishDate("2021-03-03");
        set.setParentId(226);
        long create = setDao.create(set);
        set.setId(create);
        Assert.assertNotEquals(create,-1);

        boolean delete = setDao.delete(set);
        Assert.assertTrue(delete);
    }

    @Test
    public void update() throws Exception {
        set = new Sets();
        set.setComment("create comment");
        set.setWeight(0.25);
        set.setReps(333);
        set.setStartDate("2021-03-03");
        set.setFinishDate("2021-03-03");
        set.setParentId(226);
        long create = setDao.create(set);
        set.setId(create);
        Assert.assertNotEquals(create,-1);

        set.setWeight(1.02);
        set.setReps(1);
        boolean update = setDao.update(set);
        Assert.assertTrue(update);

        Assert.assertTrue(setDao.delete(set));
    }

    @Test
    public void getByParentId() throws Exception{
        set = new Sets();
        set.setComment("getByParentId comment");
        set.setWeight(0.25);
        set.setReps(333);
        set.setStartDate("2021-03-03");
        set.setFinishDate("2021-03-03");
        long parentId = 226;
        set.setParentId(parentId);
        long create = setDao.create(set);
        set.setId(create);
        Assert.assertNotEquals(create,-1);

        List<Sets> setsList = setDao.getByParentId(parentId);
        Assert.assertTrue(setsList.size()>0);

        Assert.assertTrue(setDao.delete(set));
    }

}*/
