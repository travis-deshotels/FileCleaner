package tk.codedojo.filecleanertest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FakeClass extends FileCleaner {
    @Override
    protected String getKey(String line) {
        return line;
    }
    FakeClass(String fileName) throws IOException {
        super(fileName);
    }
}

public class MatchingRecordsTest {
    private String expectedOutput =
            "mary\n" +
            "sue\n";

    @BeforeAll
    public static void setup() throws IOException {
        String fileContents =
                "tom\n" +
                "mary\n" +
                "tom\n" +
                "bob\n" +
                "sue\n";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("myfile"))){
            bw.write(fileContents);
        }

        fileContents =
                "tom\n" +
                "bob\n";
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("myrecords"))){
            bw.write(fileContents);
        }
    }

    @AfterAll
    public static void cleanUp(){
        new File("myfile").delete();
        new File("myrecords").delete();
        new File("myoutput").delete();
    }

    @Test
    public void testingTest() throws IOException {
        FakeClass fc = new FakeClass("myfile");
        fc.removeLinesMatchingRecords("myrecords", "myoutput");
        byte[] encoded = Files.readAllBytes(Paths.get("myoutput"));
        String outString = new String(encoded, "utf-8");
        System.out.println(outString);
        assertEquals(expectedOutput, outString);
    }
}
