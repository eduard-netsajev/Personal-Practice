import java.io.IOException;
public class WriteDataToFile {
    public static void main(String[] args) throws IOException {
        java.io.File file = new java.io.File("text.txt");
        if (file.exists()) {
            System.out.println("File already exists");
            System.exit(1);
        }

        // Create a file
        java.io.PrintWriter output = new java.io.PrintWriter(file);

        // Write formatted output to the file
        output.print("Eduard was born in ");
        output.println(1993);
        output.print("Now is year ");
        output.println(2014);

        // Close the file
        output.close();
    }
}

class TryWithResources{

    public static void main(String[] args) throws IOException{
        java.io.File file = new java.io.File("text.txt");
        try (
                // Create a file
                java.io.PrintWriter output = new java.io.PrintWriter(file);
        ) {
            // Write formatted output to the file
            output.print("Eduard was born in ");
            output.println(1993);
            output.print("Now is year ");
            output.println(2014);
        }
    }
}