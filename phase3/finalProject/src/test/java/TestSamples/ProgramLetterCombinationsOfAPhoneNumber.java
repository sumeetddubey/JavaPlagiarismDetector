package TestSamples;

import java.util.ArrayList;
import java.util.List;

public class ProgramLetterCombinationsOfAPhoneNumber {
	public List<String> letterCombinations(String digits) {
        Character[] two = new Character[]{'a', 'b', 'c'};
        Character[] three = new Character[]{'d', 'e', 'f'};
        Character[] four = new Character[]{'g', 'h', 'i'};
        Character[] five = new Character[]{'j', 'k', 'l'};
        Character[] six = new Character[]{'m', 'n', 'o'};
        Character[] seven = new Character[]{'p', 'q', 'r', 's'};
        Character[] eight = new Character[]{'t', 'u', 'v'};
        Character[] nine = new Character[]{'w', 'x', 'y', 'z'};
        List<Character[]> table = new ArrayList<Character[]>();
        table.add(two);
        table.add(three);
        table.add(four);
        table.add(five);
        table.add(six);
        table.add(seven);
        table.add(eight);
        table.add(nine);
        
        List<String> re = new ArrayList<String>();
        if(digits.length() == 0) return re;
        
        int m = 0;
        while(m < digits.length() && (digits.charAt(m) == '0' || digits.charAt(m) == '1')) 
            m++;
        
        if(m == digits.length())
            return re;
        
        System.out.println(m);
        
        Character[] arr = table.get(digits.charAt(m) - '2');
        for(int j = 0; j < arr.length; j++){
            re.add("" + arr[j]);
        }
        
        for(int i = m + 1; i < digits.length(); i++){
            if(digits.charAt(i) == '0' || digits.charAt(i) == '1')
                continue;
            List<String> newRe = new ArrayList<String>();
            
            for(String s: re){
                Character[] array = table.get(digits.charAt(i) - '2');
                for(int j = 0; j < array.length; j++){
                    String newS = s + array[j];
                    newRe.add(newS);
                }
            }
            re = newRe;
        }
        
        return re;
    }
}