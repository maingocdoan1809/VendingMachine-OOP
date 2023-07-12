/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.TreeMap;

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

    private void _helper(int amount, HashMap<Integer, Integer> current, ArrayList<HashMap<Integer, Integer>> all) {

        if (amount < 0) {
            return;
        }
        if (amount == 0) {
            var newHashMap = new HashMap<Integer, Integer>();
            for(var entry : current.entrySet()) {
                newHashMap.put(entry.getKey(), entry.getValue());
            }
            current.clear();
            all.add(newHashMap);
            return;
        }
        for (Integer d : this.denom.keySet()) {
            if (d <= amount) {
                int numCash = amount / d;
                int remain = amount - numCash*d;
                System.out.println("Amount: %d,  D: %d, Numcash: %d, remain: %d".formatted(amount,d, numCash, remain));
                current.put(d, numCash);
                _helper(remain, current, all);
            }
        }

    }

    public ArrayList<HashMap<Integer, Integer>> convert(int amount) {
        var possible = new ArrayList<HashMap<Integer, Integer>>();
        var curr = new HashMap<Integer, Integer>();
        _helper(amount, curr, possible);

        return possible;
    }

    public static void main(String[] args) {
        MoneyChanger c = new MoneyChanger();

        var x = c.convert(7000);
        for (var i : x) {
            System.out.println(i);
        }
    }

}
