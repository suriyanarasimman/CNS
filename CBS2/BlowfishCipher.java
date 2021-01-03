import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
public class BlowfishCipher {
public static void main(String[] args) throws Exception {  
    KeyGenerator keygenerator = KeyGenerator.getInstance("Blowfish");

     SecretKey secretkey = keygenerator.generateKey();
    Cipher cipher = Cipher.getInstance("Blowfish");

    
    cipher.init(Cipher.ENCRYPT_MODE, secretkey);

    
    String inputText = JOptionPane.showInputDialog("Input your message: ");

    
    byte[] encrypted = cipher.doFinal(inputText.getBytes());

    cipher.init(Cipher.DECRYPT_MODE, secretkey);

    
    byte[] decrypted = cipher.doFinal(encrypted);

    
    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                                  "encrypted text: " + new String(encrypted) + "\n" +
                                  "decrypted text: " + new String(decrypted));

    
    System.exit(0);
  }
}