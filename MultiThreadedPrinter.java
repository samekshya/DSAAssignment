// Question no 6 (a)
// Description:
// This program implements a multi-threaded sequence printer that prints numbers in the order:
// 0 1 0 2 0 3 0 4 0 5 (for n = 5)
// - A zero is printed before each number (even or odd).
// - Uses thread synchronization to ensure correct sequence execution.
// - Three threads are used: one for zero, one for even numbers, and one for odd numbers.

class NumberPrinter {
    // Function to print zero
    public void printZero(int num) {
        System.out.print(num);
    }

    // Function to print even numbers
    public void printEven(int num) {
        System.out.print(num);
    }

    // Function to print odd numbers
    public void printOdd(int num) {
        System.out.print(num);
    }
}

class ThreadController {
    private int n; // Maximum number to print
    private int count = 1; // Tracks the current print step
    private final Object lock = new Object(); // Synchronization lock
    private NumberPrinter printer; // Object to handle printing

    // Constructor to initialize the number range and printer object
    public ThreadController(int n, NumberPrinter printer) {
        this.n = n;
        this.printer = printer;
    }

    // Function to print zeros in the correct sequence
    public void printZero() {
        synchronized (lock) {
            for (int i = 1; i <= n; i++) {
                while (count % 2 != 1) { // Ensure zero prints before any number
                    try {
                        lock.wait(); // Wait until it's time to print zero
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printZero(0); // Print zero
                count++; // Move to the next step
                lock.notifyAll(); // Notify other threads
            }
        }
    }

    // Function to print even numbers in the correct sequence
    public void printEven() {
        synchronized (lock) {
            for (int i = 2; i <= n; i += 2) { // Only print even numbers
                while (count % 4 != 0) { // Ensure it follows zero and an odd number
                    try {
                        lock.wait(); // Wait until it's time to print an even number
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printEven(i); // Print even number
                count++; // Move to the next step
                lock.notifyAll(); // Notify other threads
            }
        }
    }

    // Function to print odd numbers in the correct sequence
    public void printOdd() {
        synchronized (lock) {
            for (int i = 1; i <= n; i += 2) { // Only print odd numbers
                while (count % 4 != 2) { // Ensure it follows zero
                    try {
                        lock.wait(); // Wait until it's time to print an odd number
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printer.printOdd(i); // Print odd number
                count++; // Move to the next step
                lock.notifyAll(); // Notify other threads
            }
        }
    }
}

public class MultiThreadedPrinter {
    public static void main(String[] args) {
        int n = 5; // Example input
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(n, printer);

        // Create three threads for zero, even, and odd numbers
        Thread zeroThread = new Thread(controller::printZero);
        Thread evenThread = new Thread(controller::printEven);
        Thread oddThread = new Thread(controller::printOdd);

        // Start all threads
        zeroThread.start();
        evenThread.start();
        oddThread.start();
    }
}

// Summary:
// - This program ensures correct sequence execution using thread synchronization.
// - Each number is printed in the correct order: zero before every number.
// - Uses a shared lock to synchronize three threads printing zero, even, and odd numbers.
// - Implements a two-step control where even and odd numbers wait for their turn after zero.

// Expected Output (for n = 5):
// 0102030405
