package nl.avisi.techday.concurrency.labs.deadlock.solution2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Laptops worden door Developers gebruikt om te  programmeren.
 * 
 * De Laptops hebben elk een interne ReentrantLock die wordt gebruikt om op te synchronizen.
 */
class Laptop {
    
    private final Lock lock;
    public Laptop() {
        this.lock = new ReentrantLock(true);
    }
    
    public boolean tryLock() {
        return this.lock.tryLock();
    }
    
    public void unlock() {
        this.lock.unlock();
    }
}