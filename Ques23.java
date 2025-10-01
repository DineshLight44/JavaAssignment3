// MathLangDemo.java
public class MathLangDemo {
    public static void main(String[] args) {
        // Math.random() -> double in [0.0, 1.0)
        double r = Math.random();
        System.out.println("Random (0..1): " + r);

        // generate random integer between -50 and 50
        int randInt = (int) (Math.random() * 101) - 50; // -50..50
        System.out.println("Random int (-50..50): " + randInt);

        // Math.abs()
        System.out.println("Absolute of " + randInt + " is " + Math.abs(randInt));

        // Math.pow()
        double base = 2.0;
        double exponent = 8.0;
        System.out.println(base + " ^ " + exponent + " = " + Math.pow(base, exponent));

        // showing combination
        double scaled = Math.pow(Math.abs(randInt), 2);
        System.out.printf("Square of absolute(%d) = %.0f%n", randInt, scaled);
    }
}
