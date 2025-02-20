package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jakarta.servlet.http.HttpSession;

public class Operation {
	
	public Letter charToLetterInSet(String character, Set<Letter> letters) {
		for(Letter letter : letters) {
			if(letter.getLetter().equals(String.valueOf(character))) {
				return letter;
			}
		}
		
		Letter newLetter = new Letter(String.valueOf(character));
		letters.add(newLetter);
		return newLetter;
//		lettersになかったら新しく作ってSetに追加して返す
	}
	
	public Letter charToLetterInMap(char character, Map<Letter, Integer> distr) {
		for(Letter letter : distr.keySet()) {
			if(letter.getLetter().equals(String.valueOf(character))) {
				return letter;
			}
		}
		
		Letter newLetter = new Letter(String.valueOf(character));
		return newLetter;
//		lettersになかったら新しく作って返す
	}
	
	
	public void learn(String text, HttpSession session) {
		
		Set<Letter> letters = (Set<Letter>) session.getAttribute("letters");
		if (letters == null) {
	        letters = new HashSet<>();
	    }
		
        text = text.replaceAll("[\\n\\r]", "");
        text = text.replaceAll("[\\s　]+", ""); 
        text = text.replaceAll("[^\\p{L}\\p{N}。、]", ""); 
		
		for(int i = 0; i < text.length(); i++) {
			Letter thisLetter = charToLetterInSet(String.valueOf(text.charAt(i)), letters);
			thisLetter.addSum();
			letters.add(thisLetter);
			
			if(i == text.length() - 1) {
				thisLetter.addNullFreq();
			}else {
				Letter nextLetter = charToLetterInMap(text.charAt(i+1), thisLetter.getDistr());
				thisLetter.addFreq(nextLetter);
			}
		}
		
		session.setAttribute("letters", letters);
	}
	
	public Letter generateNext(Letter letter, Set<Letter> letters) {
		Random rand = new Random();
		int num = rand.nextInt(letter.getSum());
		
		for(Map.Entry<Letter, Integer> entry : letter.getDistr().entrySet()) {
	        num -= entry.getValue();
	        if(num < 0) {
	        	for(Letter letterInSet : letters) {
	        		if(entry.getKey().getLetter().equals(letterInSet.getLetter())) {
	        			return letterInSet;
	        		}
	        	}
	        }
	    }
	    return new Letter("NULL"); // どこにも当たらなかった場合
	}
	
	public Letter generateFirst(Set<Letter> letters) {
		
//		句点の次を始まりにする
		Letter kuten = charToLetterInSet("。", letters);
		kuten.addSum();
		if(letters.contains(kuten)) {
			Letter kutenNext = generateNext(kuten, letters);
			if(!kutenNext.getLetter().equals("NULL")) {
				return kutenNext;
			}
		}
//		読点の次を始まりにする
		Letter toten = charToLetterInSet("、", letters);
		toten.addSum();
		if(letters.contains(toten)) {
			Letter totenNext = generateNext(toten,  letters);
			if(!totenNext.getLetter().equals("NULL")) {
				return totenNext;
			}
		}
		
//		ランダムな文字を最初にする
		List<Letter> list = new ArrayList<>(letters);
		if(list.isEmpty()) {
	        return new Letter("NULL"); // 空の状態を考慮
	    }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
	}
	
	public void generate(HttpSession session) {
	    Set<Letter> letters = (Set<Letter>) session.getAttribute("letters");
	    
//	    letterに関する例外処理
	    if (letters == null || letters.isEmpty()) {
	        session.setAttribute("errorMsg", "何か学習させておくれ");
	        return;
	    }

//	    最初の文字を生成する
	    StringBuilder text = new StringBuilder();
	    Letter letter = generateFirst(letters);
	    
	 // 最初の文字が "NULL" の場合、即終了
	    if (letter.getLetter().equals("NULL")) {
	        session.setAttribute("errorMsg", "学習データが不十分です");
	        return;
	    }
	    
//	    終了条件に関する変数の設定
	    int kutenCount = 0;
	    int length = 1;

//	    終了条件は「10000字以上」or「5文(句点が5回)」or「NULL文字」の生成
	    while (length < 10000) {
	    	
//	    	結合
	    	text.append(letter.getLetter());
	    	
	    	if(letter.getSum() <= 0) {
	    		session.setAttribute("errorMsg", letter.getLetter() + letter.getSum());
	    		return;
	    	}
	    	
//	    	次の文字の生成とnullチェック
	        Letter nextLetter = generateNext(letter, letters);
	        if (nextLetter.getLetter().equals("NULL")) {
	            break;
	        }

//	        句点ならカウント
	        if (nextLetter.getLetter().equals("。")) {
	            kutenCount++;
	        }

//	        次の文字を今の文字に
	        letter = nextLetter;
	        length++;

	        if (kutenCount == 5) {
	        	text.append(letter.getLetter());
	            break;
	        }
	    }

//	    セッションに格納
	    session.setAttribute("text", text.toString());
	}

	public void deleteHistory(HttpSession session) {
        session.removeAttribute("letters");
        session.removeAttribute("text");
        session.removeAttribute("errorMsg");
    }

}
