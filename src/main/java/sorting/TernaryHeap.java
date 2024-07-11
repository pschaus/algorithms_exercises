package sorting;

/**
 * A max-heap is a hierarchical tree structure with the following invariants:
 *  - The tree is essentially complete, i.e., all levels of the tree are filled except possibly the right most child part of the last one,
 *  - For any node in the tree, the value associated with the node is greater or equal than the values of its children.
 *
 * Consequently, the maximum value is situated at the root and can be accessed in constant time.
 * Notably, this invariant must be maintained after insertions or removals.
 *
 * In this assignment, your task is to implement the insert, size, getMax, and delMax operations for a ternary max heap data structure
 * implemented with an array.
 * In a ternary max heap, each node can have at most three children, and all children have values lower than the parent node.
 * The tree is represented by the array `content`, where the parent-child relationship is implicitly defined by indices.
 * Specifically, a node at index i has three children at indices 3i+1, 3i+2, and 3i+3.
 * It is assumed that the root is at index 0 in the array.
 *
 * For instance, consider a heap with a capacity of 6. After inserting numbers <8,2,3,8,9> in this order, the array content should be as follows:
 *  content: [9, 8, 3, 8, 2, 0], the size = 5 and the heap looks like this :
 *
 *                                                  9
 *                                                  |
 *                                       ----------------------
 *                                       |          |         |
 *                                       8          3         8
 *                                       |
 *                                       2
 *
 * Now after deleting the max, the array content should be content : [8, 2, 3, 8, (9), 0] and the size = 4 and the heap :
 *                                                  8
 *                                                  |
 *                                        ----------------------
 *                                        |          |         |
 *                                        2          3         8
 *
 *  Notice that we left the 9 in the array, but it is not part of the heap anymore since the size is 4.
 *
 * To remove the maximum element from the heap while maintaining its structure,
 * the approach involved swapping the root with last element of the last layer (at position size-1) in the content array.
 * Subsequently, re-heapify the structure by allowing the new root to sink using swap with the largest of its children
 * until it is greater than all its children or reaches a leaf.
 *
 * Complete the implementation of the TernaryHeap class.
 *
 * The insert operation should run in O(log_3(n)) time, where n is the number of elements in the heap.
 * The delMax operation should run in O(log_3(n)) time, where n is the number of elements in the heap.
 * The getMax operation should run in O(1) time.
 *
 * Debug your code on the small examples in the test suite.
 */
public class TernaryHeap {

    // Array representing the heap. This is where all the values must be added
    // let this variable protected so that it can be accessed from the test suite
    protected int[] content;
    int size;


    /**
     * Initializes an empty max-heap with the given initial capacity.
     * @param capacity : the initial capacity of the heap
     */
    public TernaryHeap(int capacity) {
        // TODO
        this.content = new int[capacity];
        this.size = 0;
    }

    /**
     * @return the number of keys currently in the heap.
     */
    public int size() {
        // TODO
        return size;
    }

    public void swap(int ind1, int ind2){
        int temp = content[ind2];
        content[ind2] = content[ind1];
        content[ind1] = temp;
    }



    /**
     * Inserts a new key into the heap. After this method is finished, the heap-invariant must be respected.
     * @param x The key to be inserted
     */
    public void insert(int x) {
        // TODO
        System.out.printf("inserting: %d\n", x);
        if(size == content.length){
            int[] doubled = new int[content.length*2];
            for (int i = 0; i < content.length; i++) {
                doubled[i] = content[i];
            }
            content = doubled;
        }
        content[size] = x;
        if(size > 0){
            int parent = (size-1)/3;
            System.out.printf("parent: %d\n", parent);
            while(x > content[parent]){
                System.out.printf("parent: %d\n", parent);
                swap(size, (size-1)/3);
                if(parent > 0){
                    parent = (parent-1)/3;
                }else if(parent <= 0){
                    parent = 0;
                }
            }
        }
        size++;
    }

    public int chooseChild(int parent){
        int child1 = content[3*parent +1];
        int child2 = content[3*parent +2];
        int child3 = content[3*parent +3];
        if(child1 - child2 >= 0 && child1 - child3 >= 0){
            return 3*parent + 1;
        }else if(child2 - child1 >= 0 && child2 - child3 >= 0){
            return 3*parent + 2;
        }else if(child3 - child2 >= 0 && child3 - child1 >= 0){
            return 3*parent + 3;
        }
        return 0;
    }

    /**
     * Removes and returns the largest key on the heap. After this method is finished, the heap-invariant must be respected.
     * @return The largest key on the heap
     */
    public int delMax() {
        // TODO
        int toRet = content[0];
        int i = 0;
        while(3*i +3 < size){
            int child = chooseChild(i);
            swap(i, child);
            i = child;
        }
        if(3*i +2 < size){
            int child1 = content[3*i +1];
            int child2 = content[3*i +2];
            if(child1 - child2 >= 0){
                swap(i, child1);
                i = child1;
            }else{
                swap(i, child2);
                i = child2;
            }
        }else if(3*i +1 < size){
            swap(i, 3*i +1);
            i = 3*i + 1;
        }
        while(i <= size-1){
            swap(i, i+1);
            i = i+1;
        }
        size--;
        return toRet;
    }

    /**
     * @return The largest key on the heap without removing it.
     */
    public int getMax() {
        // TODO
        return content[0];
    }


}
