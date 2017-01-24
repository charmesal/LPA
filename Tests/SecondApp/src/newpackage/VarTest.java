package newpackage;

public class VarTest
{
    public static void main(String[] args)
    {
        String snowFlake = "\u2744";
        int touchdown;
        touchdown = 2465;
        ++touchdown;
        boolean boolTest = false;
        
        /*
        Order of priority
        1. ++ --
        2. * /
        3. + -
        4. ==
        5. =
        */
        
        System.out.println(snowFlake);
        System.out.println(boolTest);
        System.out.println(touchdown + 1);
        int time = (int) System.currentTimeMillis();
        //System.out.notifyAll();
        System.out.flush();
        System.out.print("\n\nnew line here\n\nsystem time: ");
        System.out.print(time);
    }
}