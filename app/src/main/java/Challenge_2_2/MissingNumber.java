package Challenge_2_2;

public class MissingNumber {
    public static int findMissingNumber(int[] nums) {
        int n = nums.length;
        // Sum of array from 1 to n+1
        int totalSumOfArray = (n + 1) * (n + 2) / 2;
        int actualSumOfArray = 0;

        for (int num : nums) {
            actualSumOfArray += num;
        }

        return totalSumOfArray - actualSumOfArray;
    }

    public static void main(String[] args) {
        int[] nums = {3, 7, 1, 2, 6, 4,8};
        System.out.println("The missing number is: " + findMissingNumber(nums));
    }
}