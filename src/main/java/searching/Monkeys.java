package searching;

import java.util.Hashtable;
import java.util.List;


/**
 * Problem 21 of AdventOfCode 2022
 *
 * The monkeys are back!
 *
 * Each monkey is given a job: either to yell a specific number or to yell the result of a math operation.
 * All of the number-yelling monkeys know their number from the start;
 * however, the math operation monkeys need to wait for two other monkeys to yell a number,
 * and those two other monkeys might also be waiting on other monkeys.
 *
 * Your job is to work out the number the monkey named 'root'
 * will yell before the monkeys figure it out themselves.
 *
 * For example:
 *
 * root: pppw + sjmn
 * dbpl: 5
 * cczh: sllz + lgvd
 * zczc: 2
 * ptdq: humn - dvpt
 * dvpt: 3
 * lfqf: 4
 * humn: 5
 * ljgn: 2
 * sjmn: drzm * dbpl
 * sllz: 4
 * pppw: cczh / lfqf
 * lgvd: ljgn * ptdq
 * drzm: hmdt - zczc
 * hmdt: 32
 *
 * Each line contains the name of a monkey, a colon, and then the job of that monkey:
 *
 * A lone number means the monkey's job is simply to yell that number.
 * A job like aaaa + bbbb means the monkey waits for monkeys aaaa and bbbb to yell each of their numbers;
 * the monkey then yells the sum of those two numbers.
 * aaaa - bbbb means the monkey yells aaaa's number minus bbbb's number.
 * Job aaaa * bbbb will yell aaaa's number multiplied by bbbb's number.
 * Job aaaa / bbbb will yell aaaa's number divided by bbbb's number.
 * These (+, -, /, *) are the only possible operators.
 * So, in the above example, monkey drzm has to wait for monkeys hmdt and zczc to yell their numbers.
 * Fortunately, both hmdt and zczc have jobs that involve simply yelling a single number,
 * so they do this immediately: 32 and 2.
 * Monkey drzm can then yell its number by finding 32 minus 2: 30.
 *
 * Then, monkey sjmn has one of its numbers (30, from monkey drzm),
 * and already has its other number, 5, from dbpl.
 * This allows it to yell its own number by finding 30 multiplied by 5: 150.
 *
 * This process continues until root yells a number: 152
 *
 * This input is inside input_advent21_debug.txt
 *
 * The parsing is already done for you.
 * What you receive is a list of Monkey (one for each line of the input) and there
 * are two types of monkey: YellingMonkey and OperationMonkey.
 *
 * Hint: use if (m instanceof YellingMonkey) to verify which instance it is
 *
 * Feel free to use existing java classes for your algorithm.
 */
public class Monkeys {


    /**
     * Compute the number for monkey named "root.
     * Your algorithm should run in O(n) where n is
     * the size of the input.
     */
    public static long evaluateRoot(List<Monkey> input) {
        // STUDENT return -1;
        // BEGIN STRIP
        Hashtable<String, Node> nodes = new Hashtable<>();

        for (Monkey m: input) {
            if (m instanceof YellingMonkey) {
                YellingMonkey monkey = (YellingMonkey) m;

                if (nodes.containsKey(monkey.name)) {
                    Node node = nodes.get(monkey.name);
                    node.res = monkey.number;
                } else {
                    nodes.put(monkey.name,new Node(monkey.name,monkey.number));
                }
            } else {
                OperationMonkey monkey = (OperationMonkey) m;

                Node leftN = null;
                Node rightN = null;

                if (nodes.containsKey(monkey.leftMonkey)) {
                    leftN = nodes.get(monkey.leftMonkey);
                } else {
                    // create left node
                    leftN = new Node(monkey.leftMonkey);
                    nodes.put(monkey.leftMonkey,leftN);
                }
                if (nodes.containsKey(monkey.rightMonkey)) {
                    rightN = nodes.get(monkey.rightMonkey);
                } else {
                    // create right node
                    rightN = new Node(monkey.rightMonkey);
                    nodes.put(monkey.rightMonkey, rightN);
                }

                if (!nodes.containsKey(monkey.name)) {
                    nodes.put(monkey.name,  new Node(monkey.name,leftN,rightN,monkey.op));
                } else {
                    Node n = nodes.get(monkey.name);
                    n.left = leftN;
                    n.right = rightN;
                    n.op = monkey.op;
                }
            }
        }
        Node root = nodes.get("root");
        evaluate(root,null);
        return nodes.get("root").res;
        // END STRIP

    }

    // BEGIN STRIP
    public static void evaluate(Node n, Node parent) {
        n.parent = parent;
        if (n.isLeaf()) return;
        else {
            evaluate(n.left,n);
            evaluate(n.right,n);
            switch (n.op) {
                case '*':
                    n.res = n.left.res * n.right.res;
                    break;
                case '/':
                    n.res = n.left.res / n.right.res;
                    break;
                case '+':
                    n.res = n.left.res + n.right.res;
                    break;
                case '-':
                    n.res = n.left.res - n.right.res;
                    break;
                default:
                    throw new IllegalArgumentException("unknown op");
            }
        }
    }



    public static class Node {

        public Node(String monkey) {
            this.monkey = monkey;
        }
        public Node(String monkey, int res) {
            this.monkey = monkey;
            this.res = res;
        }
        public Node(String monkey, Node left, Node right, char op) {
            this.monkey = monkey;
            this.left = left;
            this.right = right;
            this.op = op;
        }

        public boolean isLeaf() {
            return this.op == ' ';
        }

        public String monkey;
        public long res;
        public char op = ' ';
        public Node left;
        public Node right;

        public Node parent;
    }
    // END STRIP




    static class Monkey {
        String name;
    }
    static class YellingMonkey extends Monkey {
        int number;
        public YellingMonkey(String name,int number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return name+": "+number;
        }
    }
    static class OperationMonkey extends Monkey {
        char op;
        String leftMonkey;
        String rightMonkey;
        public OperationMonkey(String name, String left, char op, String right) {
            this.name = name;
            this.leftMonkey = left;
            this.op = op;
            this.rightMonkey = right;
        }

        @Override
        public String toString() {
            return name+": "+leftMonkey+" "+op+" "+rightMonkey;
        }
    }


}
