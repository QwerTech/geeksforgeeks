import java.util.Arrays;
import java.util.Scanner;

public class FindMinimalKArraySort {
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
        Arrays.sort(numbers);
        return numbers[k-1];
    }
}