package comparator.ast.tests;

import java.util.ArrayList;
import java.util.List;

public class TestSample5 {
	public List<String> letterCombinations(String digits) {        
        String[] strs = new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        
        List<String> re = new ArrayList<String>();
        if(digits.length() == 0) return re;
        
        re.add("");
        for(int i = 0; i < digits.length(); i++){
            if(digits.charAt(i) == '0' || digits.charAt(i) == '1')
                continue;
            List<String> newRe = new ArrayList<String>();
            for(String s: re){
                String chars = strs[digits.charAt(i) - '2'];
                for(int j = 0; j < chars.length(); j++){
                    newRe.add(s + chars.charAt(j));
                }
            }
            re = newRe;
        }
        
        return re;
    }
}