import static java.util.stream.Collectors.toCollection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class FindMinimalKStream {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int testCasesCount = in.nextInt();
        int[][] input = new int[testCasesCount][];
        int[] ks = new int[testCasesCount];
        for (int i = 0; i < testCasesCount; i++) {
            int size = in.nextInt();
            input[i] = new int[size];
            for (int j = 0; j < size; j++) {
                input[i][j] = in.nextInt();
            }
            ks[i] = in.nextInt();
        }

        for (int i = 0; i < testCasesCount; i++) {
            int result = solve(input[i], ks[i]);
            System.out.println(result);
        }
    }

    private static int solve(int[] numbers, int k) {
        TreeSet<Integer> treeSet = Arrays.stream(numbers).boxed().collect(toCollection(TreeSet::new));

        Iterator<Integer> iterator = treeSet.iterator();
        for (int i = 0; i < k - 1; i++) {
            iterator.next();
        }
        return iterator.next();
    }
}