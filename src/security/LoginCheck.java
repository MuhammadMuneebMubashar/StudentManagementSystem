package security;

import java.io.*;

public class LoginCheck {
    public static boolean matchDetails(File file, String username, String password){
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            String [] field;
            while ((line = br.readLine()) != null){
                field = line.split(", ");
                if (field[0].equals(username) && field[1].equals(password)){
                    return true;
                }
            }return false;
        }catch (IOException e){
            return false;
        }
    }
}
