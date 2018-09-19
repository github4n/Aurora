package me.aurora.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressUtilTests {

    @Test
    public void test(){
        String ip = "183.136.223.170";
        String address = AddressUtils.getCityInfo(ip);
        System.out.println("地址: " + address);
    }
}
