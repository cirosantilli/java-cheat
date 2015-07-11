/*
Non-compact switch example, thus likely to compile to `lookupswitch`.
*/

import java.util.Random;

public class SwitchVerySpread {
    public static void main(String[] args) {
        int i = new Random().nextInt() % 6;
        int j = -1;
        switch (i) {
            case 0:
                j = 0;
            break;
            case 1:
                j = 1;
            break;
            case 0x10:
                j = 2;
            break;
            case 0x100:
                j = 3;
            break;
            case 0x1000:
                j = 4;
            break;
            case 0xFFFFFFF:
                j = 5;
            break;
        };
        System.out.println(j);
    }
}
