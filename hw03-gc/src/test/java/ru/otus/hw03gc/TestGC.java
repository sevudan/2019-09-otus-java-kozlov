package ru.otus.hw03gc;


import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

/**
 * JVM start parameters:
 *
 * -Xms128m
 * -Xmx128m
 * -XX:+UseSerialGC
 *
 * -Xms128m
 * -Xmx128m
 * -XX:+UseParallelGC
 *
 * -Xms128m
 * -Xmx128m
 * -XX:+UseConcMarkSweepGC
 *
 * -Xms128m
 * -Xmx128m
 * -XX:+UseG1GC
 */

public class TestGC {

    private static long gcTotalCount = 0;

    private static long beginTime = System.currentTimeMillis();

    private static HashMap<String, String> countGc = new HashMap<>();

    private static void switchOnMonitoring() {

        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        StringBuilder sb = new StringBuilder();

        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {

                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    long duration = info.getGcInfo().getDuration();
                    long totalDuration = 0;
                    gcTotalCount = gcbean.getCollectionCount();

                    sb.append(totalDuration + duration)
                            .append(", Total Count: ").append(gcTotalCount);
                    countGc.put(gcName, sb.toString());

                    sb.delete(0, sb.length());
                }
            };
            emitter.addNotificationListener(listener, null, null);

        }

    }

    private static void view() {
        System.out.println("## GC info: ##");
        countGc.forEach((k, v) -> System.out.printf("GC name: %s, time ms: %s, \n",  k, v));
        System.out.println("Total time in sec: " + ((System.currentTimeMillis() - beginTime) / 1000));
    }

    static class Main {

        public static void main(String[] args) throws Exception{

            System.out.printf("Start time: %s \n  Starting pid: %s \n",
                    System.currentTimeMillis(),
                    ManagementFactory.getRuntimeMXBean().getName());

            switchOnMonitoring();

            TimerTask task = new TimerTask() {
                public void run() {
                    view();
                }
            };

            Timer timer = new Timer("Timer");
            timer.scheduleAtFixedRate(task, 10_000L, 30_000L);

            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("ru.otus.hw03gc:type=TestGC");
            Test mbean = new Test();

            mbs.registerMBean(mbean, name);
            mbean.start();

        }
    }
}
