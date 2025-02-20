package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Letter {
	
//	field
	private String letter;
	private int sum;
	private Map<Letter, Integer> distr;
	
//	constructotr
	public Letter(String letter) {
		this.letter = letter;
		this.sum = 0;
		this.distr = new HashMap<>();
	}
	
//	method
	public String getLetter() {
		return letter;
	}
	
	public int getSum() {
		return sum;
	}
	
	public Map<Letter, Integer> getDistr(){
		return distr;
	}
	
	public void addSum() {
		sum++;
	}
	
	public void addFreq(Letter letter) {
		distr.put(letter, distr.getOrDefault(letter, 0) + 1);
	}
	
	public void addNullFreq() {
		Letter nullLetter = new Letter("NULL");
	    distr.put(nullLetter, distr.getOrDefault(nullLetter, 0) + 1);
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Letter letter1 = (Letter) obj;
        return Objects.equals(letter, letter1.letter);
    }
	
	@Override
	public int hashCode() {
	    return Objects.hash(letter);
	}
}
