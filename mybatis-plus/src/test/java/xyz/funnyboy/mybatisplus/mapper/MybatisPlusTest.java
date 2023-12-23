package xyz.funnyboy.mybatisplus.mapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.funnyboy.mybatisplus.entity.User;

import java.util.List;

/**
 * @author VectorX
 * @version V1.0
 * @description MybatisPlus测试
 * @date 19/12/2023
 */
// 这个运行器可以执行特定的测试规则和断言方法，从而满足特定的测试需求
// @RunWith(Runner.class)
@SpringBootTest
public class MybatisPlusTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusTest.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        final List<User> userList = userMapper.selectList(null);
        LOGGER.info(userList.toString());
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("Helen");
        user.setAge(18);
        user.setEmail("55317332@qq.com");
        int result = userMapper.insert(user);
        LOGGER.info(String.valueOf(result));
        LOGGER.info(user.toString());
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(1L);
        user.setAge(28);
        int result = userMapper.updateById(user);
        LOGGER.info(String.valueOf(result));
    }

    @Test
    public void testOptimisticLocker() {
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao");
        user.setEmail("helen@qq.com");
        userMapper.updateById(user);
    }

    @Test
    public void testOptimisticLockerFail() {
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");
        //模拟取出数据后，数据库中version实际数据比取出的值大，即已被其它线程修改并更新了version
        user.setVersion(user.getVersion() - 1);
        //执行更新
        userMapper.updateById(user);
    }

    @Test
    public void testDelete() {
        final int delete = userMapper.deleteById(1L);
        LOGGER.info("delete=" + delete);
    }

    @Test
    public void testPerformance() {
        User user = new User();
        user.setName("我是Helen");
        user.setEmail("helen@sina.com");
        user.setAge(18);
        userMapper.insert(user);
    }
}
