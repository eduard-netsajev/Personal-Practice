import java.io.*;

public class Copy {

    public static void main(String[] args) {
        try (
                BufferedInputStream in = new BufferedInputStream(new DataInputStream
                        (new FileInputStream(args[0])))
        ) {
            File target = new File(args[1]);
            if (target.exists()) {
                System.out.println("Target file " + args[1] + " already " +
                        "exists");
            } else {
                BufferedOutputStream out = new BufferedOutputStream(new
                        DataOutputStream(new FileOutputStream(target)));
                {
                    int c = 0;
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                        c++;
                    }
                    System.out.println(c + " bytes copied");
                }
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Source file " + args[0] + " doesn't exist");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
