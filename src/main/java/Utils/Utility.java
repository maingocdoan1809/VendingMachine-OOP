package Utils;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Utility {

    // hàm dùng để xác định xem một chuỗi có phải là dạng số hay không
    // ví dụ '23243' là một chuỗi dạng số, nhưng '1232sdf23' thì không phải
    public static boolean isNumeric(String str) {
        Pattern t = Pattern.compile("^[1-9]+[0-9]*$");
        Matcher match = t.matcher(str);
        return match.find();
    }
    // hàm format một biến float về một chuỗi số có định dạng giống tiền tệ.
    public static String toMoney(float money) {
        Locale locale = new Locale("vn","VN");
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
        decimalFormat.setDecimalFormatSymbols(dfs);
        return decimalFormat.format(money).substring(2) + " VND";
        
    }
}
