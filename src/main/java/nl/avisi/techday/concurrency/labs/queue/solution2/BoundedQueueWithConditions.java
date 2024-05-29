package nl.avisi.techday.concurrency.labs.queue.solution2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueueWithConditions<E> {
    final E[] buf;
    int tail, head, count;

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    public BoundedQueueWithConditions(int capacity) {
        buf = (E[]) new Object[capacity];
    }

    public void put(E v) throws OverflowException, InterruptedException {
        lock.lock();
        try {
            while (count == buf.length) {
                notFull.await();
            }
            // NB: deze if is NIET nodig, alleen hier gelaten ten bewijze dat dit NOOIT meer optreedt.
            if (count == buf.length) {
                throw new OverflowException();
            }

            buf[tail] = v;
            if (++tail == buf.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws UnderflowException, InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }

            // NB: deze if is NIET nodig, alleen hier gelaten ten bewijze dat dit NOOIT meer optreedt.
            if (count == 0) {
                throw new UnderflowException();
            }
            
            E v = buf[head];
            buf[head] = null; // Niet per se nodig, maar ja, deze value is nu opgehaald en niet meer nodig
            if (++head == buf.length) {
                head = 0;
            }
            --count;
            notFull.signal();
            return v;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

}    

