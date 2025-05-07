package com.example.demo.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * lop truu tuong cho cac game
 * cung cap chuc nang va thuoc tinh cho cac tro choi
 */
abstract class AbstracGame {
    protected String wordToGuess; //tu can doan trong game
    protected char[] guessedLetters;//array chua cac chu cai da doan dung
    protected List<Character> incorrectGuesses;//list cac chu cai da doan sai
    protected int remainingGuesses;//so lan thu con lai truoc khi thua
    protected boolean gameOver;//trang thai tro choi ket thuc
    protected static final int MAX_TRIES = 6;// ra han toi da so lan thu

    public abstract void start();//bat dau tro choi, chon tu ngau nhien, khoi tao cac tham so
    public abstract void reset();//reset game, xoa cac du lieu cu va chuan bi game moi
    public abstract boolean isGameOver();//ktra xem tro choi da ket thuc chua (true neu da ket thuc, false neu chua)
    public abstract String getInstructions();//lay huong dan tro choi
    public abstract void updateDisplay();//cap nhap va hien thi trang thai hien tai cua tro choi

    /**
     * xu ly khi moi lai doan chu cai
     * @param letter
     * @return true neu doan dung, false neu sai
     */
    public abstract boolean makeGuess(char letter);

    /**
     * lay tu da hien thi voi cac chu cai da doan
     * @return chuoi hien thi tu hien tai
     */
    public abstract String getCurrentWord();

    /**
     * lay tu can doan
     * @return tu can doan
     */
    public abstract String getActualWord();

    /**
     * danh sach cac chu cai doan sai
     * @return danh sach cac chu cai doan sai
     */
    public abstract List<Character> getIncorrectGuesses();

    /**
     * so lan doan con lai
     * @return
     */
    public abstract int getRemainingTries();

    /**
     * kiem tra xem da thang chua
     * @return true neu thang, false neu chua thang
     */
    public abstract boolean isWon();


    /**
     * khoi tao cac gia trij co ban
     * dai lai danh sach da doan sai, so lan doan con lai, trang thai tro choi
     */
    protected void initializeGame() {
        System.out.println("Initializing game...");// thong bao dang khoi tao tro choi
        incorrectGuesses = new ArrayList<>();//tao danh sach cac chu cai doan sai
        remainingGuesses = MAX_TRIES;//khoi tao so lan doan max
        gameOver = false;//dat trang thai tro choi chua ket thuc
    }

    /**
     * chon 1 tu ngau nhien tu file txt
     * tao array luu cac chu cai da doan voi kich thuoc = do dai cua word can doan
     * @param filePath duong dan den file gameWords.txt
     */
    protected void selectRandomWord(String filePath) {
        try {
            List<String> words = Files.readAllLines(Paths.get(filePath));// doc tat ca cac word tu trong file txt
            wordToGuess = words.get(new Random().nextInt(words.size())).trim().toUpperCase();// chon 1 tu ramdom va chuyen thanh chu in hoa
            guessedLetters = new char[wordToGuess.length()];//tao array voi kich thuoc = tu can doan
            Arrays.fill(guessedLetters, '_');//set tat ca cac tu bat dau = "_"
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading words file : " + e.getMessage()); // bao error neu khong doc duoc file txt
        }
    }

    /**
     * kiem tra trang thai cua tro choi moi lan doan
     * game ket thuc khi het so lan doan hoac da doan dung tat ca cac chu cai
     */
    protected void checkGameStatus() {
        if (remainingGuesses <= 0) {
            gameOver = true; //game ket thuc neu het luot doan
        } else if (!String.valueOf(guessedLetters).contains("_")) {
            gameOver = true;// game ket thuc khi doan dung het tu
        }
    }

}


