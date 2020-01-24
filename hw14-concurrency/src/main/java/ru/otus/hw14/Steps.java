package ru.otus.hw14;


public class Steps {

  public static void main(String[] args) throws InterruptedException {
    Steps counter = new Steps();
    counter.go();
  }

  private void go() throws InterruptedException {

    Object monitor = new Object();

    Thread threadA = new Thread(new CounterStep(monitor));
    threadA.setName("threadA");
    threadA.setPriority(1);

    Thread threadB = new Thread(new CounterStep(monitor));
    threadB.setName("thrB");
    threadB.setPriority(5);

    threadA.start();
    threadB.start();

    threadA.join();
    threadB.join();
  }
}