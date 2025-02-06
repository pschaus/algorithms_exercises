package searching;

import java.util.LinkedList;

/**
 * You are tasked with creating part of a backend system for an online library.
 * The system uses a hash table to manage book inventory.
 * Each book is uniquely identified by its ISBN (key) and includes details such as
 * the year of production (value).
 * The hash table internally uses an array of linked lists (buckets)
 * to handle collisions through chaining.
 *
 *  The `add` method adds a book to the library store or updates it if already present.
 *  The `delete` method removes a book by its ISBN.
 *
 * Your tasks :
 *  - Update the `add` method to ensure that whenever
 *    the number of keys divided by the number of buckets exceeds the load factor,
 *    the hash table is resized by doubling the number of buckets.
 *
 *  - Implement the `delete` method to enable book removal
 *    by ISBN without affecting the hash table size.
 *
 * The add and delete methods should run in O(1) time complexity on average,
 * whenever the load factor is kept below 1.
 */
public class LibraryStore {

    public static class Book {
        int isbn; // key
        int year; // value

        public Book(int isbn, int year) {
            this.isbn = isbn;
            this.year = year;
        }

        public Book(int isbn) {
            this.isbn = isbn;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) return false;

            Book other = (Book) obj;

            return isbn == other.isbn;
        }

    }


    int size; // number of keys in the hash table
    private final double loadFactor; // the load factor that triggers resizing
    LinkedList<Book>[] buckets;

    /**
     * Constructs a library store with the specified initial capacity.
     * with a load factor of 0.75.
     * @param M the initial capacity of the library
     */
    public LibraryStore(int M) {
        this(M,0.75);
    }

    /**
     * Constructs a library store with the specified initial capacity and load factor.
     *
     * @param M          the initial capacity of the library
     * @param loadFactor the load factor that triggers resizing
     */
    public LibraryStore(int M, double loadFactor) {
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("Load factor must be positive");
        }
        buckets = new LinkedList[M];
        for (int i = 0; i < M; i++) {
            buckets[i] = new LinkedList<>();
        }
        this.loadFactor = loadFactor;
    }

    /**
     * Computes the hash for a given key.
     *
     * @param key the book's ISBN
     * @return the hash value corresponding to the key
     */
    private int hash(int key) {
        return (key & 0x7fffffff) % buckets.length;
    }


    /**
     * Adds a book to the library store or updates it if already present.
     *
     * It should resize (double the capacity of the internal array)
     * when the load factor is exceeded.
     *
     * @param isbn the book's ISBN
     * @param year the book's production year
     */
    public void add(int isbn, int year) {
        //TODO
        // BEGIN STRIP
        if ((double) size / buckets.length >= this.loadFactor) {
            resize();
        }
        // END STRIP

        LinkedList<Book> bucket = buckets[hash(isbn)];

        for (Book book : bucket) {
            if (book.isbn == isbn) {
                book.year = year;
                return;
            }
        }
        bucket.add(new Book(isbn, year));
        size++;
    }

    /**
     * Retrieves the production year of a book by its ISBN.
     *
     * @param isbn the book's ISBN
     * @return the year of production if the book is found, or null otherwise
     */
    public Integer get(int isbn) {
        LinkedList<Book> bucket = buckets[hash(isbn)];
        for (Book book: bucket) {
            if(book.isbn == isbn) {
              return book.year;
            }
        }
        return null;
    }


    /**
     * Resizes the library store when the load factor is exceeded.
     */
    private void resize(){
        // TODO
        // BEGIN STRIP
        LinkedList<Book>[] oldBuckets = buckets;
        buckets = new LinkedList[buckets.length*2];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        size = 0;
        for (LinkedList<Book> bucket : oldBuckets) {
            for (Book entry : bucket) {
                add(entry.isbn, entry.year);
            }
        }
        // END STRIP
    }

    /**
     * Deletes a book from the library store if it exists.
     *
     * @param isbn the book's ISBN
     */
    public void delete(int isbn) {
        // TODO
        // BEGIN STRIP
        LinkedList<Book> bucket = buckets[hash(isbn)];
        boolean removed = bucket.remove(new Book(isbn));
        if(removed) {
            size--;
        }
        // END STRIP
    }

    /**
     * Retrieves the current capacity of the library store.
     *
     * @return the current capacity
     */
    public int getCapacity() {
        return buckets.length;
    }

    /**
     * Retrieves the number of books in the library store.
     *
     * @return the current size
     */
    public int getSize() {
        return this.size;
    }

}
