/*
An if that could be converted to a `switch`.
*/

import java.util.Random;

public class SwitchIf {
    public static void main(String[] args) {
        int i = Math.abs(new Random().nextInt()) % 6;
        int j = -1;
        if (i == 0) {
            j = 0;
        } else if (i == 1) {
            j = 1;
        } else if (i == 2) {
            j = 2;
        } else if (i == 3) {
            j = 3;
        } else if (i == 4) {
            j = 4;
        } else if (i == 5) {
            j = 5;
        };
        System.out.println(j);
    }
}
