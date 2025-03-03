// Question no 6 a
// Description: Implements a multi-threaded sequence printer for zero, even, and odd numbers.
// Ensures correct order using thread synchronization.

class NumberPrinter {
    public void printZero(int num) {
        System.out.print(num);
    }
    public void printEven(int num) {
        System.out.print(num);
    }
    public void printOdd(int num) {
        System.out.print(num);
    }
}

class ThreadController {
    private int n;
    private int count = 1;
    private final Object lock = new Object();
    private NumberPrinter printer;

    public ThreadController(int n, NumberPrinter printer) {
        this.n = n;
        this.printer = printer;
    }

    public void printZero() {
        synchronized (lock) {
            for (int i = 1; i <= n; i++) {
                while (count % 2 != 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printZero(0);
                count++;
                lock.notifyAll();
            }
        }
    }

    public void printEven() {
        synchronized (lock) {
            for (int i = 2; i <= n; i += 2) {
                while (count % 4 != 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printEven(i);
                count++;
                lock.notifyAll();
            }
        }
    }

    public void printOdd() {
        synchronized (lock) {
            for (int i = 1; i <= n; i += 2) {
                while (count % 4 != 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printOdd(i);
                count++;
                lock.notifyAll();
            }
        }
    }
}

public class MultiThreadedPrinter {
    public static void main(String[] args) {
        int n = 5; // Example input
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n, printer);

        Thread zeroThread = new Thread(controller::printZero);
        Thread evenThread = new Thread(controller::printEven);
        Thread oddThread = new Thread(controller::printOdd);

        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}
