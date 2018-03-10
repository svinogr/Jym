package info.upump.jym.bd;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 07.03.2018.
 */
@RunWith(AndroidJUnit4.class)
public class CycleDaoTest {
    private CycleDao cycleDao;
    private Cycle cycle;

    @Before
    public void setUp() throws Exception {
        cycleDao = new CycleDao(InstrumentationRegistry.getTargetContext());
        System.out.println("setUp");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");
        cycleDao.close();
    }


    @Test
    public void create() throws Exception {
        System.out.println("create");
        cycle = new Cycle();
        cycle.setTitle("create create");
        cycle.setStartDate("2018-03-12");
        cycle.setFinishDate("2018-03-12");
        long id = cycleDao.create(cycle);
        cycle.setId(id);
        Assert.assertNotEquals(id, -1); //test create

        Assert.assertTrue(cycleDao.delete(cycle));

    }

    @Test
    public void update() {
        cycle = new Cycle();
        cycle.setTitle("test title for update");
        cycle.setStartDate("2018-05-05");
        cycle.setFinishDate("2018-05-05");

        long idCreate = cycleDao.create(cycle);
        cycle.setId(idCreate);
        Assert.assertNotEquals(idCreate, -1);//test create

        cycle.setTitle("updated test title");
        cycle.setStartDate("2006-06-06");
        cycle.setFinishDate("2006-06-06");

        boolean id = cycleDao.update(cycle);
        Assert.assertTrue(id);// test update

        Assert.assertTrue(cycleDao.delete(cycle));
    }

    @Test
    public void delete() throws Exception {
        System.out.println("tearDown");
        cycle = new Cycle();
        cycle.setTitle("test delete");
        cycle.setStartDate("2018-03-07");
        cycle.setFinishDate("2018-03-06");
        long id = cycleDao.create(cycle);
        cycle.setId(id);
        Assert.assertNotEquals(id, -1); //test create

        boolean idDelete = cycleDao.delete(cycle);
        Assert.assertTrue(idDelete);
    }
    @Test
    public void getAll() throws Exception {
        cycle = new Cycle();
        cycle.setTitle("test title getAll");
        cycle.setStartDate("2018-04-04");
        cycle.setFinishDate("2018-04-04");
        long id = cycleDao.create(cycle);
        cycle.setId(id);
        Assert.assertNotEquals(id, -1); //test create

        List<Cycle> all = cycleDao.getAll();
        Assert.assertTrue(all.size()>0);

        Assert.assertTrue(cycleDao.delete(cycle));

    }

    @Test
    public void getById() throws Exception{
            cycle = new Cycle();
            cycle.setTitle("test title getAll");
            cycle.setStartDate("2018-04-04");
            cycle.setFinishDate("2018-04-04");
            long id = cycleDao.create(cycle);
            cycle.setId(id);
            Assert.assertNotEquals(id, -1); //test create

        Cycle byId = cycleDao.getById(id);
        Assert.assertEquals(cycle.getTitle(),byId.getTitle());
        Assert.assertEquals(cycle.getComment(),byId.getComment());
        Assert.assertEquals(cycle.getStartStringFormatDate(),byId.getStartStringFormatDate());
        Assert.assertEquals(cycle.getFinishStringFormatDate(),byId.getFinishStringFormatDate());
        Assert.assertEquals(cycle.getParentId(),byId.getParentId());

        Assert.assertTrue(cycleDao.delete(cycle));
    }


}