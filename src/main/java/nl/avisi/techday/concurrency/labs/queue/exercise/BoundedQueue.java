package nl.avisi.techday.concurrency.labs.queue.exercise;

class BoundedQueue<E> {
    final E[] buf;
    int tail, head, count;

    public BoundedQueue(int capacity) {
        buf = (E[]) new Object[capacity];
    }

    public void put(E v) throws OverflowException {
        if (count == buf.length) {
            throw new OverflowException();
        }
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0;
        }
        ++count;
    }

    public E take() throws UnderflowException {
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

    public int getCount() {
        return count;
    }
}

