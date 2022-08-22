package strings;

import java.io.*;
import java.util.*;

public class IncrementalHashInstanceGenerator {
    public static void main(String[] args) {

        int k = 0;
        for (int i = 0; i < 2; i++,k++) {
            System.out.println(k);
            writeInstance("data/strings.IncrementalHash/instance_"+k,1000,100,10,3);
            writeInstance("data/strings.IncrementalHash/large_instance_"+k,(int)10E4,100,3000,65536);
        }
        for (int i = 0; i < 2; i++,k++) {
            System.out.println(k);
            writeInstance("data/strings.IncrementalHash/instance_"+k,1000,20,20,5);
            writeInstance("data/strings.IncrementalHash/large_instance_"+k,(int)10E4,200,5000,1000);
        }

        for (int i = 0; i < 2; i++,k++) {
            System.out.println(k);
            writeInstance("data/strings.IncrementalHash/instance_"+k,2000,30,40,10);
            writeInstance("data/strings.IncrementalHash/large_instance_"+k,(int)10E4,50,6000,30000);
        }

    }

    public static void writeInstance(String file, int size, int Q, int M, int maxChar) {

        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));


            p.println(size+" "+Q+" "+M+" "+maxChar);

            Random rnd = new Random();
            char [] input = new char[size];
            for (int i = 0; i < input.length; i++) {
                input[i] = (char) rnd.nextInt(maxChar);
                p.print((int) input[i]);
                p.print(" ");

            }
            p.println();

            //p.println(Arrays.toString(input).replaceAll("[\\[|\\]]", ""));
            IncrementalHashBaseLine hasherb = new IncrementalHashBaseLine(Q,M);

            int prevHash = hasherb.hash(input,0);
            p.print(prevHash+" ");

            for (int i = 1; i < input.length-M; i++) {
                int h = hasherb.hash(input,i);
                //int h = hasherb.nextHash(input,prevHash,i);
                p.print(h);
                p.print(" ");
                prevHash = h;
            }
            p.println();
            p.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    static class Instance {

        int[][] matrix;
        int from;
        Set<Integer> destination;
        int solution;

        int size;
        int Q;
        int M;
        int maxChar;

        char [] input;
        int [] hash;


        public Instance(String file) {

            try {
                Scanner dis = new Scanner(new FileInputStream(file));

                size = dis.nextInt();
                Q = dis.nextInt();
                M = dis.nextInt();
                maxChar = dis.nextInt();

                input = new char[size];
                hash = new int[size-M];

                for (int i = 0; i < size; i++) {
                    input[i] = (char) dis.nextInt();
                }
                for (int i = 0; i < size-M; i++) {
                    hash[i] = dis.nextInt();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public static class IncrementalHashBaseLine {


        private static final int R = 31;
        private int M;
        private int RM;
        private int Q;

        /**
         *
         * @param Q is the modulo to apply
         * @param M is the length of the words to hash
         */
        public IncrementalHashBaseLine(int Q, int M) {
            assert(M > 0);
            assert(Q > 0);
            this.Q = Q;
            this.M = M;
            // computes (R^(M-1))%Q
            // which might be useful for implementing nextHash
            RM = 1;
            for (int i = 1; i <= M-1; i++) {
                RM = (RM * R)%Q;
            }
        }

        /**
         * Compute the hash function on the substring
         * t[from,...,from+M-1] in O(1)
         * @param t the input array
         * @param previousHash = hash(from-1)
         * @param from such that 0 < from <= t.length-M
         * @return (t[from]*R^(M-1)+t[from+1]*R^(M-1)+...+t[from+M-1])%Q
         */
        public int nextHash(char[] t, int previousHash, int from) {
            int tmp = previousHash+Q-((t[from-1]*RM)%Q);
            return ((tmp*R)%Q+t[from+M-1])%Q;
        }


        /**
         * Compute the hash function on the substring
         * t[from,...,from+M-1] in O(M)
         * @param t the input array
         * @param from 0 <= from <= t.length-M
         * @return (t[from]*R^(M-1)+t[from+1]*R^(M-1)+...+t[from+M-1])%Q
         */
        public int hash(char[] t, int from) {
            int h = 0;
            for (int i = from; i < from+M; i++) {
                h = (R*h+t[i]) % Q;
            }
            return h;
        }

    }

}
