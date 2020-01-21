package ru.otus.hw14;


public class Steps {

  public static void main(String[] args) throws InterruptedException {
    Steps counter = new Steps();
    counter.go();
  }

  private void go() throws InterruptedException {

    Thread threadA = new Thread( new CounterStep());
    threadA.setName("threadA");

    Thread threadB = new Thread(new CounterStep());
    threadB.setName("threadB");

    threadA.start();
    threadB.start();

    threadA.join();
    threadB.join();

  }
}