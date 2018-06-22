package tk.codedojo.filecleanertest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

class CsvCleaner extends FileCleaner {
    @Override
    protected String getKey(String line) {
        return line.split(",")[0];
    }

    CsvCleaner(){
        super();
    }

    CsvCleaner(String fileName) throws IOException {
        super(fileName);
    }
}

public class CsvTest {
    private String expectedOutput =
            "line1,x\n" +
            "line2,y\n" +
            "line3,w\n";

    @BeforeClass
    public static void setup() throws IOException {
        String fileContents =
                "line1,x\n" +
                "line2,y\n" +
                "line1,z\n" +
                "line3,w\n";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("deletepls"))){
            bw.write(fileContents);
        }
    }

    @AfterClass
    public static void tearDown(){
        new File("deletepls").delete();
        new File("deleteplspls").delete();
    }

    @Test
    public void noArgConstructorTest() throws IOException {
        CsvCleaner dupRemove = new CsvCleaner();
        dupRemove.importFile("deletepls");
        dupRemove.removeDuplications("deleteplspls");
        byte[] encoded = Files.readAllBytes(Paths.get("deleteplspls"));
        String outString = new String(encoded, "utf-8");
        System.out.println(outString);
        assertEquals(expectedOutput, outString);
    }

    @Test
    public void argConstructorTest() throws IOException {
        CsvCleaner dup = new CsvCleaner("deletepls");
        dup.removeDuplications("deleteplspls");
        byte[] encoded = Files.readAllBytes(Paths.get("deleteplspls"));
        String outString = new String(encoded, "utf-8");
        System.out.println(outString);
        assertEquals(expectedOutput, outString);

    }
}
