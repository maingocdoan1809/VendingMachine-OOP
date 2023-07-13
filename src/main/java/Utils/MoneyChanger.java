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

    private  HashMap<Integer, String> denom = new HashMap<>();

    private static class BooleanWrapper {

        public boolean bool = false;
    }

    public static HashMap<Integer, String> getDenom() {
        return new MoneyChanger().denom;
    }
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

    public static List<List<Integer>> findCombinations(HashMap<Integer, Integer> shouldMatch, int[] arr, int m) {
        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(shouldMatch, combinations, new ArrayList<>(), arr, m, 0, new BooleanWrapper());
        return combinations;
    }

    public static HashMap<Integer, Integer> toHashMapCount(List<Integer> currentCombination) {
        HashMap<Integer, Integer> x = new HashMap<>();
        for (var i : currentCombination) {
            if (x.get(i) == null) {
                x.put(i, 1);
            } else {
                x.put(i, x.get(i) + 1);
            }
        }
        return x;
    }

    private static void backtrack(HashMap<Integer, Integer> shouldMatch, List<List<Integer>> combinations, List<Integer> currentCombination,
            int[] arr, int remainingSum, int start, BooleanWrapper isMatch) {
        if (remainingSum == 0) {
            var x = toHashMapCount( currentCombination);
            for (var i : x.entrySet()) {
                Integer cashValue = i.getKey();
                var max = shouldMatch.get(cashValue);
                var desired = i.getValue();
                if (max - desired < 0) {
                    return;
                }
            }
            isMatch.bool = true;
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = arr.length - 1; i >= start; i--) {
            if (arr[i] <= remainingSum && !isMatch.bool) {
                currentCombination.add(arr[i]);
                backtrack(shouldMatch, combinations, currentCombination, arr, remainingSum - arr[i], i, isMatch);
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }

    public static void main(String[] args) {

    }

}
