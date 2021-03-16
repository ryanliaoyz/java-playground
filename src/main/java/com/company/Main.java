package com.company;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static Map test = new HashMap();

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
//        main.test();
        main.sort();
    }

    public void sort() {
        Map<String, Object> unsort = new HashMap();
        unsort.put("a", 123);
        unsort.put("b", "12e");
        unsort.put("c", "13");
        unsort.put("d", 1);
        unsort.put("e", "a2c");
        Map<String, Object> sorted= unsort.entrySet().stream().peek(e -> {
            if ((!StringUtils.isNumeric(e.getValue().toString()))) {
                e.setValue("'" + e.getValue() + "'");
            }
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(sorted);
    }

    public void test() throws InterruptedException {

        Thread t0 = new Thread(() -> {

            try {
                System.out.println(getid(test));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t0.setName("waiting");
        t0.start();

        mani();
    }

    private Object getid(Map map) throws InterruptedException {
        synchronized (Thread.currentThread()) {
            while (map.get("id") == null) {

                System.out.println(Thread.currentThread());
                Thread.currentThread().wait();
            }
        }
        return map.get("id");
    }

    private void mani() {
        test.put("id", "123");
        System.out.println(Thread.currentThread());
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equals("waiting")) {
                synchronized (t) {
                    t.notify();
                    break;
                }

            }
        }
    }
}
