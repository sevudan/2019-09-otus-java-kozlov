package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CounterStep extends Thread {

  private int count = 1;

  private boolean forward = true;

  private final Object monitor;

  private static final int MAX_COUNT = 10;

  private static final Logger logger = LoggerFactory.getLogger(CounterStep.class);

  public CounterStep(Object monitor) {
    this.monitor = monitor;
  }

  private void inc() {
  count++;
 }

  private void dec() {
   count--;
  }

  private void step(){
   logger.info("step: {}", count);
  }

  @Override
  public void run() {
    while (true) {
      if (forward) {
        synchronized (monitor) {
          while (count < MAX_COUNT) {
            if (count == MAX_COUNT - 1) {
              forward = false;
            }
            step();
            inc();
            try {
              Thread.sleep(500);
              monitor.notify();
              monitor.wait();
            } catch (InterruptedException ex) {
              ex.printStackTrace();
            }
          }
        }
      }
      if (!forward) {
        synchronized (monitor) {
          while (count > 0) {
            if (count == 1) {
              forward = true;
              break;
            }
            step();
            dec();
            try {
              Thread.sleep(500);
              monitor.notify();
              monitor.wait();
            } catch (InterruptedException ex) {
              ex.printStackTrace();
            }
          }
        }
      }
    }
  }
}