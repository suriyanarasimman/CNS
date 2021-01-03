import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Encryption");
        RC5Enc enc = new RC5Enc();
        enc.encrypt();
        System.out.println("Decryption");
        RC5Dec dec = new RC5Dec();
        dec.decrypt();
    }
}

class RC5Enc {

    public void encrypt() throws Exception {
        KeyExp ke = new KeyExp();
        String s[] = ke.compute();
        System.out.println("Enter Input");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("a = ");
        String a = fullfill0(Long.toBinaryString(Long.parseLong(br.readLine(), 16)));
        System.out.print("b = ");
        String b = fullfill0(Long.toBinaryString(Long.parseLong(br.readLine(), 16)));
        a = add(a, fullfill0(s[0]));
        b = add(b, fullfill0(s[1]));
        int tmp = 0;
        for (int i = 1; i <= 12; i++) {
            tmp = Integer.parseInt("" + Long.parseLong(b, 2) % 32);
            a = xor(a, b);
            a = a.substring(tmp) + a.substring(0, tmp);
            a = add(a, fullfill0(s[2 * i]));
            tmp = Integer.parseInt("" + Long.parseLong(a, 2) % 32);
            b = xor(b, a);
            b = b.substring(tmp) + b.substring(0, tmp);
            b = add(b, fullfill0(s[(2 * i) + 1]));
            System.out.println(i + " iteration = " + (Long.toHexString(Long.parseLong(a, 2)))
                    + (Long.toHexString(Long.parseLong(b, 2))));
        }
        String out = a + b;
        System.out.println("Output = " + (Long.toHexString(Long.parseLong((out.substring(0, 32)), 2)))
                + (Long.toHexString(Long.parseLong((out.substring(32)), 2))));
    }

    public String fullfill0(String x) {
        return (get0(32 - x.length()) + x);
    }

    public String xor(String x, String y) {
        String result = "";
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i)) {
                result += "0";
            } else {
                result += "1";
            }

        }
        return result;
    }

    public String get0(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }

    public String add(String x, String y) {
        String result = "";
        boolean carry = false;
        for (int i = x.length() - 1; i >= 0; i--) {
            if ((x.charAt(i) == y.charAt(i) && carry == false) || (x.charAt(i) != y.charAt(i) && carry == true)) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            if ((x.charAt(i) == '1' && y.charAt(i) == '1')
                    || (x.charAt(i) == '1' && y.charAt(i) == '1' && carry == true)
                    || (x.charAt(i) != y.charAt(i) && carry == true)) {
                carry = true;
            } else {
                carry = false;
            }
        }
        return result;
    }
}

class RC5Dec {
    public void decrypt() throws Exception {
        KeyExp ke = new KeyExp();
        String s[] = ke.compute();
        System.out.println("Enter Input");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("a = ");
        String a = fullfill0(Long.toBinaryString(Long.parseLong(br.readLine(), 16)));
        System.out.print("b = ");
        String b = fullfill0(Long.toBinaryString(Long.parseLong(br.readLine(), 16)));
        int tmp = 0;
        for (int i = 12; i >= 1; i--) {
            b = fullfill0(Long.toBinaryString((Long.parseLong(b, 2) - Long.parseLong(s[(2 * i) + 1], 2))));
            b = b.substring(b.length() - 32);
            tmp = Integer.parseInt("" + Long.parseLong(a, 2) % 32);
            b = b.substring(b.length() - tmp) + b.substring(0, b.length() - tmp);
            b = xor(b, a);
            a = fullfill0(Long.toBinaryString((Long.parseLong(a, 2) - Long.parseLong(s[2 * i], 2))));
            a = a.substring(a.length() - 32);
            tmp = Integer.parseInt("" + Long.parseLong(b, 2) % 32);
            a = a.substring(a.length() - tmp) + a.substring(0, a.length() - tmp);
            a = xor(a, b);
            System.out.println(i + " iteration = " + (Long.toHexString(Long.parseLong(a, 2)))
                    + (Long.toHexString(Long.parseLong(b, 2))));
        }
        a = fullfill0(Long.toBinaryString((Long.parseLong(a, 2) - Long.parseLong(s[0], 2))));
        b = fullfill0(Long.toBinaryString((Long.parseLong(b, 2) - Long.parseLong(s[1], 2))));
        String output = a + b;
        output = output.substring(output.length() - 64);
        System.out.println("Output = " + (Long.toHexString(Long.parseLong(output.substring(0, 32), 2)))
                + (Long.toHexString(Long.parseLong(output.substring(32), 2))));
    }

    public String fullfill0(String x) {
        return (get0(32 - x.length()) + x);
    }

    public String xor(String x, String y) {
        String result = "";
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == y.charAt(i)) {
                result += "0";
            } else {
                result += "1";
            }

        }
        return result;
    }

    public String get0(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }
}

class KeyExp {
    String s[] = new String[26];

    public static void main(String[] args) throws Exception {
        KeyExp k = new KeyExp();
        k.compute();
        for (int i = 0; i < 26; i++) {
            System.out.println(k.s[i]);
        }

    }

    public String[] compute() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter uKey = ");
        String ukey = fullfill0(br.readLine());
        // String s[] = new String[26];
        String l[] = new String[4];
        s[0] = Long.toBinaryString(Long.parseLong("B7E15163", 16));// (Pw)
        for (int i = 1; i < s.length; i++) {
            s[i] = add(s[i - 1], fullfill0(Long.toBinaryString(Long.parseLong("9E3779B9", 16))));// S[i] = S[i-1]+
                                                                                                 // 0x9E3779B9 (Qw)
        }
        for (int i = 0; i < l.length; i++) {
            l[i] = (Long.toBinaryString(Long.parseLong(ukey.substring((3 - i) * 8, (((3 - i) + 1) * 8)), 16)));// S[i] =
                                                                                                               // S[i-1]+
                                                                                                               // 0x9E3779B9
                                                                                                               // (Qw)
        }
        for (int i = 0; i < l.length; i++) {
            System.out.println("l = " + (Long.toHexString(Long.parseLong(l[i], 2))));
        }

        int i = 0, j = 0;
        String a = "", b = "", temp = "";
        for (int count = 0; count < 78; count++) {
            // A = S[i] = (S[i] + A + B) <<< 3;
            temp = add(fullfill0(s[i]), add(fullfill0(a), fullfill0(b)));
            a = s[i] = temp.substring(3) + temp.substring(0, 3);
            // B = L[j] = (L[j] + A + B) <<< (A + B);
            temp = add(fullfill0(l[j]), add(fullfill0(a), fullfill0(b)));
            long le = (Long.parseLong(add(fullfill0(a), fullfill0(b)), 2)) % 32;
            int len = Integer.parseInt("" + le);
            b = l[j] = temp.substring(len) + temp.substring(0, len);
            i = (i + 1) % 26;
            j = (j + 1) % 4;
        }
        return s;
    }

    public String fullfill0(String x) {
        return (get0(32 - x.length()) + x);
    }

    public String get0(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            result += "0";
        }
        return result;
    }

    public String add(String x, String y) {
        String result = "";
        boolean carry = false;
        for (int i = x.length() - 1; i >= 0; i--) {
            if ((x.charAt(i) == y.charAt(i) && carry == false) || (x.charAt(i) != y.charAt(i) && carry == true)) {
                result = "0" + result;
            } else {
                result = "1" + result;
            }
            if ((x.charAt(i) == '1' && y.charAt(i) == '1')
                    || (x.charAt(i) == '1' && y.charAt(i) == '1' && carry == true)
                    || (x.charAt(i) != y.charAt(i) && carry == true)) {
                carry = true;
            } else {
                carry = false;
            }
        }
        return result;
    }

}