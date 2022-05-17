/*
package info.upump.jym.bd;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import info.upump.jym.entity.User;

*/
/**
 * Created by explo on 12.03.2018.
 *//*

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDao userDao;
    private User user;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDao(InstrumentationRegistry.getTargetContext());
        user = new User();
        user.setName("вася");
        user.setWeight(10.56);
        user.setHeight(183);
        user.setNeck(100.3);
        user.setPectoral(100.3);
        user.setLeftBiceps(100.3);
        user.setAbs(100.3);
        user.setRightLeg(100.3);
        Calendar calendar = Calendar.getInstance();
           calendar.add(Calendar.DATE, -5);

        user.setDate(calendar.getTime());

    }

    @After
    public void tearDown() throws Exception {
        userDao.close();
    }

    @Test
    public void getAll() throws Exception {
        long create = userDao.create(user);
        user.setId(create);
        Assert.assertNotEquals(create, -1);

        List<User> setsList = userDao.getAll();
        Assert.assertTrue(setsList.size() > 0);

        Assert.assertTrue(userDao.delete(user));

    }

    @Test
    public void create() throws Exception {
        long create = userDao.create(user);
        user.setId(create);
        Assert.assertNotEquals(create, -1);

     //   Assert.assertTrue(userDao.delete(user));
    }

    @Test
    public void delete() throws Exception {
        long create = userDao.create(user);
        user.setId(create);
        Assert.assertNotEquals(create, -1);

        Assert.assertTrue(userDao.delete(user));
    }

    @Test
    public void update() throws Exception {
        long create = userDao.create(user);
        user.setId(create);
        Assert.assertNotEquals(create, -1);

        user.setName("васяUP");
        user.setWeight(10.20);
        user.setHeight(180);
        user.setNeck(100.56);
        user.setPectoral(100.56);
        user.setRightLeg(100.56);
        user.setAbs(100.56);
        user.setRightLeg(100.56);
        user.setDate(new Date());
        Assert.assertTrue(userDao.update(user));

        Assert.assertTrue(userDao.delete(user));
    }

    @Test
    public void getById() throws Exception {
        long create = userDao.create(user);
        user.setId(create);
        Assert.assertNotEquals(create, -1);

        User byId = userDao.getById(create);
        Assert.assertEquals(user.getName(), byId.getName());
        Assert.assertEquals(user.getWeight(), byId.getWeight(),0.0);
        Assert.assertEquals(user.getHeight(), byId.getHeight(),0.0);
        Assert.assertEquals(user.getNeck(), byId.getNeck(),0.0);
        Assert.assertEquals(user.getPectoral(), byId.getPectoral(),0.0);
        Assert.assertEquals(user.getRightBiceps(), byId.getRightBiceps(),0.0);
        Assert.assertEquals(user.getAbs(), byId.getAbs(),0.0);
        Assert.assertEquals(user.getLeftLeg(), byId.getLeftLeg(),0.0);
        Assert.assertEquals(user.getStringFormatDate(), byId.getStringFormatDate());

        Assert.assertTrue(userDao.delete(user));

    }

    @Test
    public void getByParentId() throws Exception {
    }

}*/
