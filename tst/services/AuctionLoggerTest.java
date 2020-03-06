package services;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AuctionLoggerTest {

    private String fileName;

    @Before
    public void setUp(){
        this.fileName =  "log/log.txt";
    }

    @Test
    public void test_write_log() {

        AuctionLogger auctionLogger = AuctionLogger.getInstance();

        String message = "write log";

        auctionLogger.log(fileName, message);

        assertTrue(auctionLogger.findMessage(fileName, message));

        assertThat(auctionLogger.returnMessage(fileName, message), is(message));

    }

    @After
    public void tearDown() {
        File newdir = new File(this.fileName);
        newdir.delete();
    }

}
