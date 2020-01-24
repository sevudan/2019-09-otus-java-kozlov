package ru.otus.hw14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CounterStep extends Thread {

  private int count = 1;

  private boolean forward = true;

  Object monitor = new Object();

  private static final Logger logger = LoggerFactory.getLogger(CounterStep.class);

  private static final int MAX_COUNT = 10;

  private void inc() {
  count++;
 }

  private void dec() {
   count--;
  }

  private void step(){
   logger.info("step#: {}", count);
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
              monitor.wait(500);
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
              monitor.wait(500);
            } catch (InterruptedException ex) {
              ex.printStackTrace();
            }
          }
        }
      }
    }
  }
}