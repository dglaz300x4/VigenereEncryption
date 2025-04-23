import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Vigenere {
    private Map <Character, Vector <Character>> encrypt;
    private long seed_Val;
    private String key;
    private Random rand;
    private int max_Char_Val;
    private int min_Char_Val;
    private int char_Range;

    Vigenere(){ // Constructor to create a default cipher with a randomized seed.
        key = new String();
        default_range();
        create_Vigenere(0);
    }

    Vigenere(int seed){ // Constructor to create a cipher identical to another using its seed value.
        key = new String();
        default_range();
        create_Vigenere(seed);
    }

    Vigenere(Vigenere copy_Vigenere){ // Constructor that copies info from another Vigenere class.
        key = new String();
        this.encrypt = copy_Vigenere.encrypt;
        this.seed_Val = copy_Vigenere.seed_Val;
        this.key = copy_Vigenere.key;
        this.char_Range = copy_Vigenere.char_Range;
        this.max_Char_Val = copy_Vigenere.max_Char_Val;
        this.min_Char_Val = copy_Vigenere.min_Char_Val;
    }

    //* Private functions.
    private void default_range(){
        max_Char_Val = 90;
        min_Char_Val = 65;
        char_Range = max_Char_Val-min_Char_Val+1;
    }

    private void set_Random(long seed){ // Set the seed value for randomizing the cipher square.

        if (seed == 0){
            seed = System.currentTimeMillis();
            rand = new Random(seed);
        }
        else {
            rand = new Random(seed);
            seed_Val = seed;
        }
    }

    private void create_Vigenere(long seed){ // Creates a version of the vigenere square cipher as a map with characters as keys and vectors of chars as the rows.
        Vector <Character> row;
        set_Random(seed);
        

        encrypt = new HashMap<Character, Vector <Character>>();

        for (int i = min_Char_Val; i < max_Char_Val+1; ++i){  
            row = new Vector<Character>();
            row.add((char)i); // Adds the first character to the vector 
    
            for (int a = 0; a < char_Range-1; ++a){ // Add characters to a row to be added to map.
                int add_Char = rand.nextInt(char_Range)+min_Char_Val;
                while (row.contains((char)add_Char)){
                    add_Char = rand.nextInt(char_Range)+min_Char_Val;
                }
                row.add((char)add_Char);
            }
    
            encrypt.put((char)i, row);            
        }
    }


    //* Public functions.
    public void set_Custom_Range(int min, int max){ // This funct allows the use of a custom range. This can cause problems if not done carefully.
        min_Char_Val = min;
        max_Char_Val = max;
        char_Range = max-min+1;
        create_Vigenere(this.seed_Val);
    }

    public void reset_Custom_Range(){ // Resets the min, max, and char range values back to their defualts.
        default_range();
        create_Vigenere(this.seed_Val);
    }

    public void set_Key(String key_Set){ // Sets the key value for decryption.
        if (min_Char_Val == 65 && max_Char_Val == 90){
            key = key_Set.toUpperCase(); 
        }
        else{
            key = key_Set;
        }

        key.strip();
    }
    
    public long get_Seed(){ // Get the seed value used to create the cipher.
        return this.seed_Val;
    }

    public String get_Key(){ // Returns the key for the cipher.
        return key;
    }

    public String encrypt_Message(String message){ // Encrypts the message using the map.
        String encrypted_Message_Rtn = new String();
        message = message.strip();
        if (min_Char_Val == 65 && max_Char_Val == 90){
            message = message.toUpperCase(); 
        }
        
        
        int key_Length = key.length();
        if (key.isEmpty()){
            System.err.println("There is no key set for Vigenere. Call the function '[your Vigenere variable name].set_Key'");
        }
        else {
            int code_Pos = 0;
            int message_Length = message.length();
            for (int i = 0; i < message_Length; ++i){
                if (code_Pos == key_Length){
                    code_Pos = 0;
                }
                while (message.charAt(i) == ' '){ // Ignores spaces to help with Vigenere.
                    ++i;
                }
                encrypted_Message_Rtn += encrypt.get(key.charAt(code_Pos)).get(((int)message.charAt(i))-min_Char_Val);
                
                ++code_Pos;
            }
        }


        return encrypted_Message_Rtn;
    }

    public void print_Square_For_Manual_Decrypt(){ // Prints the square for the user to manually decrypt.
        
        String out = new String();
        for (int i = min_Char_Val; i < max_Char_Val+1; ++i){
            out += ((char)i);
            out += ' ';
            System.out.print(out);
            out = "";
        }
        System.out.println();

        for (int key = min_Char_Val; key < max_Char_Val+1; ++key){
            for (int col = 0; col < char_Range; ++col){
                out += encrypt.get((char)key).elementAt(col);
                out += ' ';
                System.out.print(out);
                out = "";
            }
            System.out.println(); 
        }

    }

}
