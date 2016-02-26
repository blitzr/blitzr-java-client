package com.blitzr;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>A Generator is an Iterable object.</p>
 * <p>In the Blitzr Client we will use it to automatically call the API when needed.
 * The generator will call the API and get a list of <b>n</b> elements, we will iterate on this list and when we
 * will reach the end of this list, the Generator will automatically ask for the next elements. So we have an
 * infinite Iterable Object.</p>
 *
 * <em>Example : </em>
 *
 * <pre><code>
 *     Client blitzr = new Client(yourApiKey);
 *     Generator<Artist> artists = blitzr.getArtistBandsGenerator("bertrand-sebenne", null, null, null);
 *     for (Artist artist : artists) {
 *         System.out.println(artist.getName());
 *     }
 * </code></pre>
 *
 * @param <T>
 */
public abstract class Generator<T> implements Iterable<T> {
    private class Condition {
        private boolean isSet;
        public synchronized void set() {
            isSet = true;
            notify();
        }
        public synchronized void await() throws InterruptedException {
            try {
                if (isSet)
                    return;
                wait();
            } finally {
                isSet = false;
            }
        }
    }

    static ThreadGroup THREAD_GROUP;

    Thread producer;
    private boolean hasFinished;
    private final Condition itemAvailableOrHasFinished = new Condition();
    private final Condition itemRequested = new Condition();
    private T nextItem;
    private boolean nextItemAvailable;
    private RuntimeException exceptionRaisedByProducer;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return waitForNext();
            }
            @Override
            public T next() {
                if (!waitForNext())
                    throw new NoSuchElementException();
                nextItemAvailable = false;
                return nextItem;
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            private boolean waitForNext() {
                if (nextItemAvailable)
                    return true;
                if (hasFinished)
                    return false;
                if (producer == null)
                    startProducer();
                itemRequested.set();
                try {
                    itemAvailableOrHasFinished.await();
                } catch (InterruptedException e) {
                    hasFinished = true;
                }
                if (exceptionRaisedByProducer != null)
                    throw exceptionRaisedByProducer;
                return !hasFinished;
            }
        };
    }

    protected abstract void run() throws InterruptedException;

    protected void yield(T element) throws InterruptedException {
        nextItem = element;
        nextItemAvailable = true;
        itemAvailableOrHasFinished.set();
        itemRequested.await();
    }

    private void startProducer() {
        assert producer == null;
        if (THREAD_GROUP == null)
            THREAD_GROUP = new ThreadGroup("generatorfunctions");
        producer = new Thread(THREAD_GROUP, new Runnable() {
            @Override
            public void run() {
                try {
                    itemRequested.await();
                    Generator.this.run();
                } catch (InterruptedException e) {
                    // No need to do anything here; Remaining steps in run()
                    // will cleanly shut down the thread.
                } catch (RuntimeException e) {
                    exceptionRaisedByProducer = e;
                }
                hasFinished = true;
                itemAvailableOrHasFinished.set();
            }
        });
        producer.setDaemon(true);
        producer.start();
    }

    @Override
    protected void finalize() throws Throwable {
        producer.interrupt();
        producer.join();
        super.finalize();
    }
}
