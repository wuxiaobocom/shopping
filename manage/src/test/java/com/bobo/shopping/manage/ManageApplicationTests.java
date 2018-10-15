package com.bobo.shopping.manage;

import com.bobo.shopping.manage.common.ThreadPoolHelper;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageApplicationTests {

    @Test
    public void contextLoads() {
        Optional<Integer> of = Optional.of(null);
    }

    @Test
    public void testGuavaInt() {
        List<Integer> list = new ArrayList<>();
        Preconditions.checkArgument(list.size() >10,"wuxiaoob");
    }
    @Test
    public void guava() {
        Optional<Integer> absent = Optional.absent();
        System.err.println("absent======"+absent.isPresent());
        Optional<Integer> of = Optional.of(123);
        Integer integer = of.get();
        System.err.println(integer);
        System.err.println(of.hashCode());
        Set<Integer> set = of.asSet();
        System.err.println(set.toString());
        Optional<Class<Integer>> classOptional = Optional.fromNullable(Integer.class);
        Class<Integer> integerClass = classOptional.get();
        Package aPackage = integerClass.getPackage();
        System.err.println(aPackage.getName());
        System.err.println(classOptional.isPresent());
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
