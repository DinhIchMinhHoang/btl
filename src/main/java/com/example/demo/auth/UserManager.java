
package com.example.demo.auth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * lop quan ly nguoi dung
 * medthod: dang ky, dang nhap, cap nhap thong tin
 */
public class UserManager {
    //duong dan den file luu tru user.txt
    private static final String UserFile = "demo/src/main/resources/data/user.txt";
    //nguoi dung dang nhap vao he thong
    private static User currentUser;

    /**
     * khoi tao quan ly nguoi dung moi
     * tu dong tao file txt luu tru neu khong ton tai file luu tru
     */
    public UserManager() {
        createFileIfNotExists();//tao file txt
    }

    /**
     * tao file luu tru nguoi dung neu khong ton tai
     * tao cac folder neu can thiet
     */
    private void createFileIfNotExists() {
        try {
            File file = new File(UserFile);
            if (!file.exists()) {
                file.getParentFile().mkdirs();//tao thu muc cha
                file.createNewFile();//tao file moi
                System.out.println("Created new user file at: " + UserFile);//thong bao neu tao file
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());//bao loi neu khong tao duoc file
            e.printStackTrace();
        }
    }

    /**
     * Lay danh sach tat ca nguoi dung tu file txt
     * doc va chuyen cac thong tin thanh cac doi tuong user
     * @return list cac doi tuong user
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();//tao danh sach rong
        try {
            File file = new File(UserFile);
            if (!file.exists()) {
                System.out.println("File does not exist: " + UserFile);
                return users;//tra ve array rong neu file khong ton tai
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));//cai nay de doc file txt
            String line;
            System.out.println("Reading users from file: " + UserFile);
            while ((line = reader.readLine()) != null) {
                System.out.println("Line read: '" + line + "'");
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");//tach cac phan tu trong dong bang dau phay
                    if (parts.length >= 2) {
                        if (parts.length == 2) {
                            //ten nguoi dung va mat khau
                            users.add(new User(parts[0].trim(), parts[1].trim()));
                        } else if (parts.length >= 3) {
                            //them thong tin diem so (cai nay tinh win streak trong game)
                            users.add(new User(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim())));
                        }
                        System.out.println("Added user: " + parts[0].trim());
                    }
                }
            }
            reader.close();//dong file txt sau khi doc xong
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return users;// tra va danh sach nguoi dung
    }

    /**
     * them 1 nguoi dung
     * ktra xem nguoi dung da ton tai hay chua
     * @param user doi tuong user can them
     * @return true neu them thanh cong, false neu tan nguoi dung ton tai hoac co error
     */
    public boolean addUser(User user) {
        createFileIfNotExists();//ktra xem file co ton tai khong

        //kiem tra trung ten nguoi dung khong
        List<User> existingUsers = getAllUsers();
        for (User existingUser : existingUsers) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                System.out.println("Username already exists: " + user.getUsername());
                return false;//khong cho dang ky neu ten da ton tai
            }
        }

        //them user moi vao cuoi file
        try (FileWriter fw = new FileWriter(UserFile, true);//cai nay giup ghi thong tin vao file
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(user.toString());//ghi thong tin vao file
            System.out.println("Added new user: " + user.getUsername());//thong bao neu them thanh cong
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());//bao error khi khong them duoc
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xac thuc thon tin dang nhap
     * so sanh ten dang nhap va mat khau voi data trong file
     * @param username ten dang nhap can xac thuc
     * @param password mat khau can xac thuc
     * @return true neu thong tin hop le, false neu khong
     */
    public boolean verifyUser(String username, String password) {
        List<User> users = getAllUsers();//lay danh sach nguoi dung
        System.out.println("\nVerifying user: " + username);
        System.out.println("Stored users:");
        for (User user : users) {
            System.out.println("User in file: '" + user.getUsername() + "' , '" + user.getPassword() + "'");
            //neu ten nguoi dung va mat khau trung khop
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Match found!");
                currentUser = user;//luu nguoi dung hien tai
                return true;
            }
        }
        System.out.println("No match found.");//thong bao neu khong tim thay nguoi dung
        return false;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Upddate thong ke nguoi dung sau khi hoan thanh tro choi
     * @param user nguoi dung can cap nhap
     * @param won true neu thang, false neu thua
     */
    public void updateUserStats(User user, boolean won) {
        if (user == null) return;//khong lam gi neu user==null

        if (won) {
            user.incrementWins();//so tran thang
        } else {
            user.incrementLosses();//so tran thua
        }

        updateUserInFile(user);//update vao file txt
    }

    /**
     * update thong tin nguoi dung trong file
     * thay the thong tin moi
     * @param updatedUser user da cap nhap
     */
    public void updateUserInFile(User updatedUser) {
        List<User> users = getAllUsers();//lay danh sach nguoi dung
        try (FileWriter fw = new FileWriter(UserFile);//viet file
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (User user : users) {
                //neu
                if (user.getUsername().equals(updatedUser.getUsername())) {
                    out.println(updatedUser.toString());//ghi thong tin da dang nhap
                } else {
                    out.println(user.toString());//giu nguyen thong tin
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating user stats: " + e.getMessage());//bao loi neu co error
            e.printStackTrace();
        }
    }

    /**
     * lay danh sach nguoi co diem cao nhat
     * sap xep thu tu giam dan
     * @param limit so nguoi dung toi da can lay
     * @return danh sach nguoi dung co diem cao nhat
     */
    public List<User> getTopUsers(int limit) {
        List<User> users = getAllUsers();//Lay danh sach nguoi dung
        users.sort((u1, u2) -> Integer.compare(u2.getStreak(), u1.getStreak()));//sap xep theo diem giam dan

        if (users.size() <= limit) {
            return users;//neu so luong it hon gioi han -> tra ve toan bo danh sach
        } else {
            return users.subList(0, limit);//cat bot danh sach theo gioi han
        }
    }

    /**
     * xoa nguoi dung khoi he thong
     * @param username ten dang nhap can xoa
     * @return true neu xoa thanh cong, false neu khong tim thay hoac co error
     */
    public boolean deleteUser(String username) {
        List<User> users = getAllUsers();//lay danh sach nguoi dung
        boolean userFound = false;//danh dau tim thay nguoi dung

        try (FileWriter fw = new FileWriter(UserFile);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (User user : users) {
                if (!user.getUsername().equals(username)) {
                    out.println(user.toString());//Giu lai nguoi dung hong can xoa
                } else {
                    userFound = true;//danh dau da tim thay nguoi dung can xoa
                    System.out.println("Deleted user: " + username);
                }
            }

            return userFound;//tra ve ket qua xoa
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;//loi khi ghi file
        }
    }
}
