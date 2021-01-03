import java.util.Scanner;

class HillCipher {
    public static int[][] keymat = new int[][] { { 1, 2, 1 }, { 2, 3, 2 }, { 2, 2, 1 } }; 
    public static int[][] invkeymat = new int[][] { { -1, 0, 1 }, { 2, -1, 0 }, { -2, 2, -1 } };
    public static String key = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String encode(char a, char b, char c) {
        String ret = "";
        int x, y, z;
        int posa = (int) a - 65;
        int posb = (int) b - 65;
        int posc = (int) c - 65;
        x = posa * keymat[0][0] + posb * keymat[1][0] + posc * keymat[2][0];
        y = posa * keymat[0][1] + posb * keymat[1][1] + posc * keymat[2][1];
        z = posa * keymat[0][2] + posb * keymat[1][2] + posc * keymat[2][2];
        a = key.charAt(x % 26);
        b = key.charAt(y % 26);
        c = key.charAt(z % 26);
        ret = "" + a + b + c;
        return ret;
    }

    private static String decode(char a, char b, char c) {
        String ret = "";
        int x, y, z;
        int pa = (int) a - 65;
        int pb = (int) b - 65;
        int pc = (int) c - 65;
        x = pa * invkeymat[0][0] + pb * invkeymat[1][0] + pc * invkeymat[2][0];
        y = pa * invkeymat[0][1] + pb * invkeymat[1][1] + pc * invkeymat[2][1];
        z = pa * invkeymat[0][2] + pb * invkeymat[1][2] + pc * invkeymat[2][2];
        a = key.charAt((x % 26 < 0) ? (26 + x % 26) : (x % 26));
        b = key.charAt((y % 26 < 0) ? (26 + y % 26) : (y % 26));
        c = key.charAt((z % 26 < 0) ? (26 + z % 26) : (z % 26));
        ret = "" + a + b + c;
        return ret;
    }

    public static void main(String[] args) throws java.lang.Exception {
        String msg;
        String enc = "";
        String dec = "";
        int n;
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the text:");
        msg = myObj.nextLine();
        System.out.println("Input message : " + msg);
        msg = msg.toUpperCase();
        msg = msg.replaceAll("\\s", "");
        n = msg.length() % 3;
        if (n != 0) {
            for (int i = 1; i <= (3 - n); i++) {
                msg += 'X';
            }
        }
        System.out.println("padded message : " + msg);
        char[] pdchars = msg.toCharArray();
        for (int i = 0; i < msg.length(); i += 3) {
            enc += encode(pdchars[i], pdchars[i + 1], pdchars[i + 2]);
        }
        System.out.println("encoded message : " + enc);
        char[] dechars = enc.toCharArray();
        for (int i = 0; i < enc.length(); i += 3) {
            dec += decode(dechars[i], dechars[i + 1], dechars[i + 2]);
        }
        System.out.println("decoded message : " + dec);
    }
}