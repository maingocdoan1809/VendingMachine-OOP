/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MoneyChanger {

    private HashMap<Integer, String> denom = new HashMap<>();

    public MoneyChanger() {
        denom.put(1000, "Một nghìn đồng");
        denom.put(2000, "Hai nghìn đồng");
        denom.put(5000, "Năm nghìn đồng");
        denom.put(10000, "Mười nghìn đồng");
        denom.put(20000, "Hai Mươi nghìn đồng");
        denom.put(50000, "Năm mươi nghìn đồng");
        denom.put(100000, "Một trăm nghìn đồng");
        denom.put(200000, "Hai trăn nghìn đồng");
        denom.put(500000, "Năm trăm nghìn đồng");
    }

    public static List<List<Integer>> findCombinations(int[] arr, int m) {
        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), arr, m, 0);
        return combinations;
    }

    private static void backtrack(List<List<Integer>> combinations, List<Integer> currentCombination,
            int[] arr, int remainingSum, int start) {
        if (remainingSum == 0) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = arr.length - 1; i >= start; i--) {
            if (arr[i] <= remainingSum) {
                currentCombination.add(arr[i]);
                backtrack(combinations, currentCombination, arr, remainingSum - arr[i], i);
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1000, 2000, 5000, 10000, 20000, 50000, 100000, 200000, 500000};
        int m = 13000;

        List<List<Integer>> combinations = findCombinations(arr, m);
        // combinations.sort((a, b) -> {
        // if (a.size() > b.size()) {
        // return 1;
        // }
        // return -1;
        // });
        // Print the combinations
        for (List<Integer> combination : combinations) {
            System.out.println(combination);
        }
    }

}
