package info.upump.jym;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    CycleDao cycleDao;
    Cycle cycle;
    @Before
    public void setUp() throws Exception {
        cycleDao = new CycleDao(InstrumentationRegistry.getTargetContext());
        cycle = new Cycle();
        cycle.setTitle("test title");
        cycle.setStartDate("2018-03-07");
        cycle.setFinishDate("2018-03-06");
    }

    @After
    public void tearDown() throws Exception {
  //      cycleDao.close();
    }



    @Test
    public void create() throws Exception {
        long id = cycleDao.create(cycle);
        cycle.setId(id);
        Assert.assertNotEquals(id,-1);

    }

   /* @Test
    public void delete() throws Exception{
        boolean id = cycleDao.delete(cycle);
        Assert.assertTrue(id);
    }*/
}
