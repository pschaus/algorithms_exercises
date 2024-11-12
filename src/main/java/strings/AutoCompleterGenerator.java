package strings;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class AutoCompleterGenerator {

    public static void main(String[] args) {
        Random r = new Random(658853);
        for (int instance_id = 0; instance_id < 10; instance_id++) {
            String instance_file = "data/strings.AutoCompleter/in_" + instance_id;
            Instance instance = getRandomInstance(r);
            System.out.println("Instance " + instance_id + ":");
            System.out.println("    dict. len.: " + instance.dictionary.size());
            System.out.println("    req. len. : " + instance.requests.size());
            double count = 0;
            for (String out : instance.outputs) {
                if (out != null) {
                    count++;
                }
            }
            System.out.println("    out. hit rate: " + count / instance.outputs.size());
            writeInstance(instance_file, instance);
        }
    }

    private static class Instance {
        List<String> dictionary = new ArrayList<>();
        List<String> requests = new ArrayList<>();
        List<String> outputs = new ArrayList<>();
    }

    private static String indexToString(int i) {
        return String.valueOf((char) (i + 97));
    }

    private static Instance getRandomInstance(Random r) {
        Instance instance = new Instance();

        Set<String> set_words = new HashSet<>();
        while (set_words.size() < 100_000) {
            int w_l = r.nextInt(8) + 1;
            StringBuilder sb = new StringBuilder();
            for (int w_i = 0; w_i < w_l; w_i++) {
                sb.append(indexToString(r.nextInt(26)));
            }
            set_words.add(sb.toString());
        }
        instance.dictionary.addAll(set_words);

        AutoCompleterBaseLine ac = new AutoCompleterBaseLine(instance.dictionary.toArray(new String[0]));
        Set<String> set_hit_requests = new HashSet<>();
        while (set_hit_requests.size() < 40_000) {
            String path = ac.getRandomPath(r);
            String sub_path = path.substring(0, r.nextInt(path.length()+1));
            set_hit_requests.add(sub_path);
        }
        instance.requests.addAll(set_hit_requests);

        Set<String> set_miss_requests = new HashSet<>();
        while (set_miss_requests.size() < 10_000) {
            int w_l = r.nextInt(8) + 1;
            StringBuilder sb = new StringBuilder();
            for (int w_i = 0; w_i < w_l; w_i++) {
                sb.append(indexToString(r.nextInt(26)));
            }
            if (ac.complete(sb.toString()) == null) {
                set_miss_requests.add(sb.toString());
            }
        }
        instance.requests.addAll(set_miss_requests);

        for (String request : instance.requests) {
            instance.outputs.add(ac.complete(request));
        }
        return instance;
    }

    private static void writeInstance(String instance_file, Instance instance) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(instance_file));
            p.println(instance.dictionary.size());
            for (String word : instance.dictionary) {
                p.println(word);
            }
            p.println(instance.requests.size());
            for (int i = 0; i < instance.requests.size(); i++) {
                if (instance.outputs.get(i) != null) {
                    p.println(instance.requests.get(i) + " " + instance.outputs.get(i));
                } else {
                    p.println(instance.requests.get(i));
                }
            }
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class AutoCompleterBaseLine {
        private static int R = 26;
        private Node root = new Node();

        private static class Node {
            boolean is_key = false;
            private Node[] next = new Node[R];
        }

        private static int charToIndex(char c) {
            return ((int) c) - 97;
        }

        private static char indexToChar(int i) {
            return (char) (i + 97);
        }

        public AutoCompleterBaseLine(String[] dictionary) {
            for (String word : dictionary) {
                this.insert(word);
            }
        }

        public String getRandomPath(Random r) {
            StringBuilder sb = new StringBuilder();
            Node current = this.root;
            while (true) {
                List<Integer> filled_idx = new ArrayList<>();
                for (int i = 0; i < R; i++) {
                    if (current.next[i] != null) {
                        filled_idx.add(i);
                    }
                }
                if (filled_idx.isEmpty()) {
                    break;
                }
                int r_idx = r.nextInt(filled_idx.size());
                sb.append(indexToString(filled_idx.get(r_idx)));
                current = current.next[filled_idx.get(r_idx)];
            }
            return sb.toString();
        }

        private Node insert(Node node, String word, int d) {
            if (node == null) {
                node = new Node();
            }
            if (d == word.length()) {
                node.is_key = true;
                return node;
            }
            char c = word.charAt(d);
            node.next[charToIndex(c)] = insert(node.next[charToIndex(c)], word, d+1);
            return node;
        }



        private void insert(String word) {
            this.root = insert(root, word, 0);
        }

        private static class StringNode {
            String prefix;
            Node node;

            StringNode(String prefix, Node node) {
                this.prefix = prefix;
                this.node = node;
            }
        }

        private Node findLastNode(String word) {
            Node current = this.root;
            for (int i = 0; i < word.length(); i++) {
                if (current.next[charToIndex(word.charAt(i))] != null) {
                    current = current.next[charToIndex(word.charAt(i))];
                } else {
                    return null;
                }
            }
            return current;
        }

        public String complete(String prefix) {
            Node node = this.findLastNode(prefix);
            if (node == null) {  // Cannot find prefix in trie
                return null;
            }
            if (node.is_key) {  // The prefix is a key in the trie
                return prefix;
            }
            Queue<StringNode> q = new LinkedList<>();
            q.add(new StringNode(prefix, node));
            while (!q.isEmpty()) {
                StringNode current = q.poll();
                if (current.node.is_key) {
                    return current.prefix;
                }
                for (int i = 0; i < R; i++) {
                    if (current.node.next[i] != null) {
                        q.add(new StringNode(current.prefix + indexToChar(i), current.node.next[i]));
                    }
                }
            }
            return null;
        }
    }
}
