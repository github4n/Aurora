package me.aurora;

import me.aurora.domain.User;
import me.aurora.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuroraApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void test() throws Exception {
        User user = userService.findById(1L);
        System.out.println(user.toString());
    }
}
