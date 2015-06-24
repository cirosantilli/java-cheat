/*
# switch

# tableswitch

# lookupswitch

    http://stackoverflow.com/questions/10287700/difference-between-jvma-lookupswitch-and-tableswitch

    This is a compact example, thus likely to compile to `tableswitch`.
*/

public class Switch {
    public static void main(String[] args) {
        int i = 1;
        int j = -1;
        switch (i) {
            case 0:
                j = 0;
            break;
            case 1:
                j = 1;
            break;
            case 2:
                j = 4;
            break;
        }
        System.out.println(j);
    }
}
