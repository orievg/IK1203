import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Login
{
    private static String Login = "admin";
    private static String Password = "admin";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Are you a registered user (enter 0 if you arent, 1 if you are)");
        Integer reg = Integer.parseInt(in.nextLine());
        if (reg.equals(0)){
            System.out.println("Enter your desired login\n");
            String login = in.nextLine();

            System.out.println("Enter your password\n");
            String pw = in.nextLine();
            //create a credentials file for this login
            try {
                File file = new File(login + ".txt");
                if (file.createNewFile()) {
                    System.out.println("User added: " + file.getName().substring(0,file.getName().length()-4));
                    FileWriter fw = new FileWriter(file.getName());
                    fw.write(pw);
                    fw.close();
                }
                else {
                    System.out.println("You are already registered");
                }
            } catch (IOException e) {
                System.out.println("Bruh");
                e.printStackTrace();
            }


        }
        else if (reg.equals(1)) {
            for(int i=0;i<1;i++){
                System.out.println("Enter your login\n");
                String login = in.nextLine();
                try {
                    File myObj = new File(login + ".txt");
                    Scanner rf = new Scanner(myObj);
                    String Password = rf.nextLine();
                    rf.close();
                    System.out.println("Enter your password\n");
                    String pw = in.nextLine();
                    if (pw.equals(Password)) {
                        System.out.println("Yay");
                    } else {
                        System.out.println("Wrong password");
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("No such user found");
                    e.printStackTrace();
                }
            }
        }
        else{
            System.out.println("Invalid input");
        }

    }
}
