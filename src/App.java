import java.util.Scanner;


public class App {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        Vigenere create_Encrypt = new Vigenere();

        create_Encrypt.set_Custom_Range(33, 126);

        create_Encrypt.print_Square_For_Manual_Decrypt();

        System.out.print("Enter a key to encrypt the message: ");
        create_Encrypt.set_Key(input.nextLine()); // Input key

        
 
        System.out.print("Enter a message to be decrypted: ");



        System.out.println(create_Encrypt.encrypt_Message(input.nextLine()));
        input.close();
    }
}
