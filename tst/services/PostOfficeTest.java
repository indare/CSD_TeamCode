package services;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PostOfficeTest {

    @Test
    public void test_can_find_email() {
        PostOffice postOffice = PostOffice.getInstance();
        postOffice.clear();

        String address;
        String message;

        address = "hoge@hoge.com";
        message = "yay";
        postOffice.sendEMail(address, message);

        assertThat(postOffice.size(),is(1 ));

        postOffice.sendEMail("y@y.com", "a");
        assertThat(postOffice.size(),is(2 ));

        String logString = String.format( "<sendEMail address=\"%s\" >%s</sendEmail>\n", address, message );
        String actual = postOffice.findEmail(address,"a");

        assertThat(actual, is(logString));

        assertTrue(postOffice.doesLogContain(address,message));

    }
}
