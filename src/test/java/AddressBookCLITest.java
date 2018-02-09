import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.Processor;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class AddressBookCLITest {

    AddressBookCLI cli;

    @Before
    public void setup(){
        cli = new AddressBookCLI();
    }

    @Test
    public void onStartupStartShouldReturnWelcomeMessage(){
        String welcomeMessage = cli.start();

        String[] lines = welcomeMessage.split("\n");

        assertEquals(cli.WELCOME_MESSAGE, lines[0]);
    }

    @Test
    public void onStartupStartShouldReturnAddContactOption() {
        String welcomeMessage = cli.start();

        String[] lines = welcomeMessage.split("\n");

        assertEquals("Add Contact", lines[1]);
    }

//    @Test
//    public void afterAddContactUserShouldBePromptedForAFirstName() throws Exception{
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.out));
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.in));
//
//
//        Processor test = new Processor(new FileReader("C:/tmp/tests.txt"));
//
//
//        System.setIn(fakeIn);
//
//        cli.start();
//        writer.write("Add Contact");
//        writer.flush();
//
//        String prompt = reader.readLine();
//
//
//        assertEquals("Enter First Name", prompt);
//    }
}
