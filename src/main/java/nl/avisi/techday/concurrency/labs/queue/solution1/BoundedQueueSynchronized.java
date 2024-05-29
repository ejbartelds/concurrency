package nl.avisi.techday.concurrency.labs.queue.solution1;

class BoundedQueueSynchronized<E> {
    final E[] buf;
    int tail, head, count;

    public BoundedQueueSynchronized(int capacity) {
        buf = (E[]) new Object[capacity];
    }

    public synchronized void put(E v) throws OverflowException, InterruptedException {
        if (count == buf.length) {
            throw new OverflowException();
        }
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    public synchronized E take() throws UnderflowException, InterruptedException {
        if (count == 0) {
            throw new UnderflowException();
        }
        E v = buf[head];
        buf[head] = null; // Niet per se nodig, maar ja, deze value is nu opgehaald en niet meer nodig
        if (++head == buf.length) {
            head = 0;
        }
        --count;
        return v;
    }

    public synchronized int getCount() {
        return count;
    }
}

