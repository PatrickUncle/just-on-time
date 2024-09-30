package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class JustOnTimeTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JustOnTimeTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( JustOnTimeTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        String directoryPath = "myDirectory";
        String fileName = "myFile.txt";
        String filePath = directoryPath + File.separator + fileName;

        // 创建目录
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String hello = filePath + "_2023";
        // 创建文件并写入内容
        try (FileWriter writer = new FileWriter(hello)) {
            writer.write("Hello, World!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
