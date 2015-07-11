/*
Non-compact switch example, thus likely to compile to `lookupswitch`.
*/

import java.util.Random;

public class SwitchSpread {
    public static void main(String[] args) {
        int i = Math.abs(new Random().nextInt()) % 6;
        int j = -1;
        switch (i) {
            case 0:
                j = 0;
            break;
            case 1:
                j = 1;
            break;
            case 2:
                j = 2;
            break;
            case 4:
                j = 3;
            break;
            case 8:
                j = 4;
            break;
            case 16:
                j = 5;
            break;
        };
        System.out.println(j);
    }
}
