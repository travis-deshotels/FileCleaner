package tk.codedojo.filecleanertest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FakeDupCleaner extends FileCleaner {
    @Override
    protected String getKey(String line) {
        return line;
    }

    FakeDupCleaner(){
    }

    FakeDupCleaner(String fileName) throws IOException {
        super(fileName);
    }
}

public class IntegrationTest {
    private String expectedOutput =
                "line1\n" +
                "line2\n" +
                "line3\n";

    @BeforeAll
    public static void setup() throws IOException {
        String fileContents =
                "line1\n" +
                "line2\n" +
                "line1\n" +
                "line3\n";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("deleteme"))){
            bw.write(fileContents);
        }
    }

    @AfterAll
    public static void tearDown(){
        new File("deleteme").delete();
        new File("deletemealso").delete();
    }

    @Test
    public void noArgConstructorTest() throws IOException {
        FakeDupCleaner dupRemove = new FakeDupCleaner();
        dupRemove.importFile("deleteme");
        dupRemove.removeDuplications("deletemealso");
        byte[] encoded = Files.readAllBytes(Paths.get("deletemealso"));
        String outString = new String(encoded, StandardCharsets.UTF_8);
        System.out.println(outString);
        assertEquals(expectedOutput, outString);
    }

    @Test
    public void argConstructorTest() throws IOException {
        FakeDupCleaner dup = new FakeDupCleaner("deletemealso");
        dup.removeDuplications("deletemealso");
        byte[] encoded = Files.readAllBytes(Paths.get("deletemealso"));
        String outString = new String(encoded, StandardCharsets.UTF_8);
        System.out.println(outString);
        assertEquals(expectedOutput, outString);

    }
}
