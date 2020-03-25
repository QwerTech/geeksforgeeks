import java.util.Scanner;
import java.util.function.BiPredicate;

public class FindMinimalKArrayBigSmall {

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
        return k > numbers.length / 2 ? findBiggest(numbers, numbers.length - k + 1) : findSmallest(numbers, k);
    }

    private static int findSmallest(int[] numbers, int k) {
        Integer prevSmallest = null;
        for (int j = 0; j < k; j++) {
            int smallest = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                int item = numbers[i];
                if (prevSmallest != null && smallest <= prevSmallest && item > prevSmallest) {
                    smallest = item;
                }
                if (item < smallest && (prevSmallest == null || prevSmallest < item)) {
                    smallest = item;
                }
            }
            prevSmallest = smallest;
        }
        return prevSmallest;
    }

    private static int findBiggest(int[] numbers, int k) {
        Integer prevSmallest = null;
        for (int j = 0; j < k; j++) {
            int smallest = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                int item = numbers[i];
                if (prevSmallest != null && (smallest > prevSmallest || smallest == prevSmallest) && prevSmallest > item) {
                    smallest = item;
                }
                if (item > smallest && (prevSmallest == null || prevSmallest > item)) {
                    smallest = item;
                }
            }
            prevSmallest = smallest;
        }
        return prevSmallest;
    }


}