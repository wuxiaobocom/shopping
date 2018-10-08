package com.bobo.shopping.manage;

import com.bobo.shopping.manage.common.ThreadPoolHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void Test1 () throws Exception {
        for (int i =0 ;i< 100;i++) {
            ThreadPoolHelper.getInstance().execute("bobo",()->{
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"==========="+"bobo");
                return true;
            });
        }

        Thread.sleep(10000);
        for (int i =0 ;i< 100;i++) {
            ThreadPoolHelper.getInstance().execute("hehe",()->{
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName()+"==========="+"hehe");
                return true;
            });
        }
    }

}
class ThreadTest implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}
