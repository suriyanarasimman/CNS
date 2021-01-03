import java.util.Scanner;

class DiffieHellman {
    public static void main(String args[]) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter the P value:");
        int p = myObj.nextInt(); /* publicly known (prime number) */
        System.out.println("Enter the g value:");
        int g = myObj.nextInt(); /* publicly known (primitive root) */
        System.out.println("Enter the x value:");
        int x = myObj.nextInt(); /* only Alice knows this secret */
        System.out.println("Enter the y value:");
        int y = myObj.nextInt(); /* only Bob knows this secret */
        double aliceSends = (Math.pow(g, x)) % p;
        double bobComputes = (Math.pow(aliceSends, y)) % p;
        double bobSends = (Math.pow(g, y)) % p;
        double aliceComputes = (Math.pow(bobSends, x)) % p;
        double sharedSecret = (Math.pow(g, (x * y))) % p;
        System.out.println("simulation of Diffie-Hellman key exchange algorithm\n---------------------------------------------");
        System.out.println("Alice Sends : " + aliceSends);
        System.out.println("Bob Computes : " + bobComputes);
        System.out.println("Bob Sends : " + bobSends);
        System.out.println("Alice Computes : " + aliceComputes);
        System.out.println("Shared Secret : " + sharedSecret);
        /* shared secrets should match and equality is transitive */
        if ((aliceComputes == sharedSecret) && (aliceComputes == bobComputes))
            System.out.println("Success: Shared Secrets Matches! " + sharedSecret);
        else
            System.out.println("Error: Shared Secrets does not Match");
    }
}
// use 23 5 4 3