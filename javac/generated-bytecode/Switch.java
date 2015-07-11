/*
# switch

# tableswitch

# lookupswitch

    http://stackoverflow.com/questions/10287700/difference-between-jvma-lookupswitch-and-tableswitch

    This is a compact example, thus likely to compile to `tableswitch`.
*/

import java.util.Random;

public class Switch {
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
            case 3:
                j = 3;
            break;
            case 4:
                j = 4;
            break;
            case 5:
                j = 5;
            break;
        }
        System.out.println(j);
    }
}
