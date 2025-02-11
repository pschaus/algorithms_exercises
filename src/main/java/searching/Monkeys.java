package searching;

import java.util.HashMap;
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
     * Compute the number for monkey named "root".
     * Your algorithm should run in O(n) where n is
     * the size of the input.
     */
    public static long evaluateRoot(List<Monkey> input) {
        // STUDENT return -1;
        // BEGIN STRIP
        HashMap<String, Monkey> monkeys = new HashMap<>();
        for (Monkey monkey : input) {
            monkeys.put(monkey.name, monkey);
        }
        return monkeys.get("root").yell(monkeys);
        // END STRIP
    }


    static abstract class Monkey {
        String name;

        // BEGIN STRIP
        // Method implemented by monkeys to compute the result
        public abstract long yell(HashMap<String, Monkey> monkeys);
        // END STRIP
    }

    static class YellingMonkey extends Monkey {
        int number;

        public YellingMonkey(String name,int number) {
            this.name = name;
            this.number = number;
        }

        // BEGIN STRIP
        @Override
        public long yell(HashMap<String, Monkey> monkeys) {
            return number;
        }
        // END STRIP

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

        // BEGIN STRIP
        @Override
        public long yell(HashMap<String, Monkey> monkeys) {
            long leftRes = monkeys.get(leftMonkey).yell(monkeys);
            long rightRes = monkeys.get(rightMonkey).yell(monkeys);
            switch (op) {
                case '+':
                    return leftRes + rightRes;
                case '-':
                    return leftRes - rightRes;
                case '*':
                    return leftRes * rightRes;
                case '/':
                    return leftRes / rightRes;
                default:
                    throw new IllegalArgumentException("unknown op");
            }
        }
        // END STRIP

        @Override
        public String toString() {
            return name+": "+leftMonkey+" "+op+" "+rightMonkey;
        }
    }


}
