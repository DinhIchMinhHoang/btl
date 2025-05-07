package com.example.demo.game;
import java.util.List;

/**
 * class trien khai tro choi Hangman
 * player doan cac chu cai cua tu
 * moi lan sai se mat 1 mang
 * tro choi ket thuc khi player het luot doan hoac da doan het tu
 */
public class HangmanGame extends AbstracGame {
    /**
     * tao tro choi Hangman moi
     * khoi tao tro choi va chon 1 tu ngau nhien trong file txt
     */
    public HangmanGame() {
        initializeGame();//khoi tao cac gia tri tu lop cha
        selectRandomWord("demo/src/main/resources/data/gameWords.txt"); // Correct file path

    }

    /**
     * bat dau tro choi
     * cai nay chi de dev check xem game bat dau chua thoi
     */
    @Override
    public void start() {
        System.out.println("Hangman Game started!");//hien thong bao
    }

    /**
     * reset tro choi ve trang thai ban dau
     * xoa cac chu cai doan sai, dat lai so lan doan, chon tu moi
     */
    @Override
    public void reset() {
        incorrectGuesses.clear();//xoa tat ca cac chu cai doan sai
        remainingGuesses = MAX_TRIES;//reset so lan doan
        gameOver = false;//reset trang thai tro choi
        selectRandomWord("demo/src/main/resources/data/gameWords.txt");//chon lai tu ngau nhien
    }

    /**
     * kiem tra xem tro choi ket thuc chua
     * @return true neu da ket thuc, false neu chua
     */
    @Override
    public boolean isGameOver() {
        return gameOver;//tra ve trang thai ket thuc cua tro choi
    }

    @Override
    public String getInstructions() {
        return "Guess the word by suggesting letters";
    }

    @Override
    public void updateDisplay() {
        System.out.println("Updating Hangman display...");
    }

    /**
     * xu ly moi lan doan chu cai
     * kiem tra xem chu cai co trong tu can doan hay khong
     * cap nhap trang thai tro choi
     * @param letter chu cai player doan
     * @return true neu doan dung, false neu doan sai
     */
    @Override
    public boolean makeGuess(char letter) {
        if (gameOver) {
            return false;// neu game ket thuc thi khong cho doan nua
        }
        letter = Character.toUpperCase(letter);//chuyen chu cai thanh chu hoa de so sanh
        boolean correctGuess = false;//bien kiem tra doan dung
        //kiem tra xem chu cai co trong tu can doan hay khong
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                guessedLetters[i] = letter;//cap nhap chu cai vao vi tri tuong ung
                correctGuess = true;//danh dau la da doan dung
            }
        }
        //xu ly truong hop doan sai
        if (!correctGuess) {
            if (!incorrectGuesses.contains(letter)) {
                incorrectGuesses.add(letter);//them chu cai vao danh sach doan sai
                remainingGuesses--;//giam so lan doan con lai
            }
        }
        checkGameStatus();//kiem tra trang thai game sau lan doan
        return correctGuess;//kiem tra ket qua doan (dung hay sai)
    }

    /**
     * lay word dang duoc hien thi voi cac chu cai da doan
     * cac chuc cai chua doan se hien thi la "_"
     * @return chuoi hien thi tu hien tai
     */
    @Override
    public String getCurrentWord() {
        return String.valueOf(guessedLetters);//chuyen array chu cai da doan thanh chuoi
    }

    /**
     * lay tu can doan
     * @return tu can doan
     */
    @Override
    public String getActualWord() {
        return wordToGuess;//tra ve word nguoi choi can doan
    }

    /**
     * lay danh sach cac chuc cai doan sai
     * @return danh sach cac chu cai doan sai
     */
    @Override
    public List<Character> getIncorrectGuesses() {
        return incorrectGuesses; //tra ve danh sach cac chu cai doan sai
    }

    /**
     * lay so lan doan con lai
     * @return so lan doan con lai
     */
    @Override
    public int getRemainingTries() {
        return remainingGuesses;//tra ve so lan doan con lai
    }

    /**
     * kiem tra xem nguoi choi da thang chua
     * @return true neu thang, false neu chua thang/thua
     */
    @Override
    public boolean isWon() {
        return gameOver && !String.valueOf(guessedLetters).contains("_");//kiem tra dieu kien thang
    }
}
