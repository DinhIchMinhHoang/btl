package com.example.demo.game;

import com.example.demo.auth.User;
import com.example.demo.auth.UserManager;
import com.example.demo.common.TransitionController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

/**
 * controller cho giao dien tro choi Hangman
 */
public class HangManGameController extends TransitionController {

    @FXML
    public JFXButton GameButton;

    public Text wordDisplay, messageDisplay;

    public JFXButton restartButton;

    public Circle head;

    public Line body, leftArm, rightArm, leftLeg, rightLeg;

    public List<javafx.scene.Node> bodyParts;

    @FXML
    private VBox leaderboardBox;

    @FXML
    private VBox leaderboardEntries;

    private UserManager userManager;


    @FXML
    private JFXButton btnQ, btnW, btnE, btnR, btnT, btnY, btnU, btnI, btnO, btnP,
            btnA, btnS, btnD, btnF, btnG, btnH, btnJ, btnK, btnL,
            btnZ, btnX, btnC, btnV, btnB, btnN, btnM;


    private HangmanGame game; //doi tuong tro choi Hangman
    private Map<Character, JFXButton> letterButtons;//map lien ket giua cac chu cai button tuong ung


    /**
     * phuong thuc khoi tao giao dien tro choi
     * cai dat tro choi, button, stick man, leaderbroad
     * @param location vi tri duoc su dung de giai quyet duong dan tuong doi
     * @param resources tai nguyen duoc su dung de dia phuong hoa
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);//goi ham khoi tao tu lop cha
        game = new HangmanGame();// tao doi tuong tro choi moi
        userManager = new UserManager();// tao doi tuong quan ly nguoi dung

        //tao list cac bo phan cua stickmam
        bodyParts = new ArrayList<>();
        bodyParts.add(head);
        bodyParts.add(body);
        bodyParts.add(leftArm);
        bodyParts.add(rightArm);
        bodyParts.add(leftLeg);
        bodyParts.add(rightLeg);
        bodyParts.forEach(part -> {
            part.setOpacity(0);//an cac bo phan cua stickman
            fadeStickmanPart(part);//tao hieu ung transition
        });

        initializeGame();//khoi tao cac thanh phan cua tro choi
        slideInTransition(wordDisplay, false);// hieu ung truot vao khi hien thi tu
        slideInTransition(messageDisplay, true);//hieu ung truot vao cho phan hien thi thong bao
        animateLetterButtons();//hieu ung cho cac button chu cai

        updateLeaderboard();//update bang xep hang
    }

    /**
     * update leaderbroad top5 player co winstreak cao nhat
     */
    private void updateLeaderboard() {
        leaderboardEntries.getChildren().clear();//xoa cac muc cu trong bxh

        List<User> topUsers = userManager.getTopUsers(5);//lay top5

        //tao bang leaderbroad
        for (User user : topUsers) {
            Text entry = new Text(user.getUsername() + ": " + user.getStreak() + " streak");//lay top5 (user: number streak)
            entry.setFont(Font.font("System", 14));//phong chu
            entry.setFill(Color.DARKBLUE);//mau

            leaderboardEntries.getChildren().add(entry);//them muc vao bxh
        }

        leaderboardEntries.setPadding(new Insets(10, 0, 0, 0));//spacing
    }


    /**
     * animation cho stick man
     * @param node bo phan can ap dung hieu ung
     */
    private void fadeStickmanPart(Node node) {
        //thoi luong transition
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);//lap lai 2 lan vi neu opacity ve 1 thi se ko co hieu ung hangman
        fadeTransition.setAutoReverse(true);//reverse de opacity ve 0

        node.setOpacity(0.0);
        fadeTransition.play();//bat dau hieu ung
    }

    /**
     * transition cho cac chu cai
     * button se xuat hien tung cai 1
     */
    private void animateLetterButtons() {
        letterButtons.forEach((letter, button) -> {
            button.setOpacity(0);//cac nut trong suot tu ban dau
            //thoi luong transition
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), button);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            int index = letter - 'A';//cac chu cai suat hien tung chu 1(tao hieu ung dep)
            fadeIn.setDelay(Duration.millis(50 * index));//xep theo thu tu
            fadeIn.play();//bat dau hieu ung
        });
    }

    /**
     * khoi tao cac thanh pha cua tro choi
     * gan su kien cho cac button va reset button
     */
    private void initializeGame() {
        letterButtons = new HashMap<>();// tao map cho cac letters button

        //gan cac button
        letterButtons.put('Q', btnQ);
        letterButtons.put('W', btnW);
        letterButtons.put('E', btnE);
        letterButtons.put('R', btnR);
        letterButtons.put('T', btnT);
        letterButtons.put('Y', btnY);
        letterButtons.put('U', btnU);
        letterButtons.put('I', btnI);
        letterButtons.put('O', btnO);
        letterButtons.put('P', btnP);
        letterButtons.put('A', btnA);
        letterButtons.put('S', btnS);
        letterButtons.put('D', btnD);
        letterButtons.put('F', btnF);
        letterButtons.put('G', btnG);
        letterButtons.put('H', btnH);
        letterButtons.put('J', btnJ);
        letterButtons.put('K', btnK);
        letterButtons.put('L', btnL);
        letterButtons.put('Z', btnZ);
        letterButtons.put('X', btnX);
        letterButtons.put('C', btnC);
        letterButtons.put('V', btnV);
        letterButtons.put('B', btnB);
        letterButtons.put('N', btnN);
        letterButtons.put('M', btnM);

        //gan su kien cho tung button
        letterButtons.forEach((letter, button) -> {
            button.setOnAction(e -> handleLetterClick(letter, button));//khi player nhan nut
        });

        //gan su kien cho restart button
        restartButton.setOnAction(e -> restartGame());
        updateDisplay();//reset
    }

    /**
     * xu ly su kien khi player bam vao button
     * @param letter chu cai duoc chon
     * @param button button tuong ung
     */
    private void handleLetterClick(Character letter, JFXButton button) {
        if (!game.isGameOver()) {//neu tro choi chua ket thuc
            boolean correct = game.makeGuess(letter);//kiem tra xem doan dung hay sai

            button.setDisable(true);//vo hieu hoa nut da duoc chon
            if (correct) {
                //doi sang mau xanh nau correct
                button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            } else {
                //doi sang mau do neu sai
                button.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                int incorrectGuesses = game.getIncorrectGuesses().size() - 1;//dem lai so lan doan sai
                if (incorrectGuesses >= 0 && incorrectGuesses < bodyParts.size()) {
                    bodyParts.get(incorrectGuesses).setOpacity(1);//hien thi stick man
                }
            }

            updateDisplay();
        }
    }

    /**
     * update trang thai tro choi
     */
    private void updateDisplay() {
        //update hien thi word can doan
        StringBuilder displayText = new StringBuilder();
        String currentWord = game.getCurrentWord();
        for (int i = 0; i < currentWord.length(); i++) {
            displayText.append(currentWord.charAt(i)).append(" ");//them dau cach giua cac chu de biet xem word co bao nhieu chu cai
        }
        wordDisplay.setText(displayText.toString().trim());//hien thi tu
        wordDisplay.setStyle("-fx-font-size: 40px; -fx-font-family: monospace; -fx-letter-spacing: 1px;");//cai dat font, size

        //kiem tra trang thai ket thuc tro choi
        if (game.isGameOver()) {
            boolean won = game.isWon();//kiem tra xem thang hay thua
            if (won) {
                //hien thi thong bao neu thang
                messageDisplay.setText("Congratulations! You won!");
                messageDisplay.setFill(Color.GREEN);
            } else {
                //hien thi thong bao neu thua va cho biet tu chinh xac la gi
                messageDisplay.setText("Game Over! The word was: " + game.getActualWord());
                messageDisplay.setFill(Color.RED);
            }

            //update user (cai nay de luu ket qua win streak)
            User currentUser = userManager.getCurrentUser();
            if (currentUser != null) {
                userManager.updateUserStats(currentUser, won);//update user's stat
                updateLeaderboard();//update leaderbroad
            }
        }
    }

    /**
     * restart tro choi
     */
    private void restartGame() {
        game.reset();// reset game
        // reset cac button
        letterButtons.values().forEach(button -> {
            button.setDisable(false);//enable button
            button.setStyle("");//cai nay them cho chac =))
        });
        messageDisplay.setText(""); //xoa thong bao tren label
        bodyParts.forEach(part -> part.setOpacity(0)); //hide stickman
        updateDisplay();
        updateLeaderboard();
    }

    @FXML
    protected void onGameButtonClick() {
    }

    /**
     * restart button
     * restart game
     * vi du dang choi nhung thay tu nay kho qua bam restart de choi tu khac
     */
    @FXML
    protected void onRestartButtonClick() {
        game.reset();//restart tro choi
        //dat lai trang thai cac nut
        letterButtons.values().forEach(button -> {
            button.setDisable(false);//enable letters button
            button.setStyle("");
        });
        messageDisplay.setText("");//xoa thong bao
        bodyParts.forEach(part -> part.setOpacity(0));//hide stickman
        updateDisplay();
        updateLeaderboard();
    }
}
