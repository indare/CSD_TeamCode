package application.closeFactory.closeProcessor.output;

import services.AuctionLogger;

import static application.parameters.Parameters.LOG_FILE;

public class Log extends Output{
    private String message;

    public Log(String message) {
        this.message = message;
    }

    @Override
    public void run(){
        AuctionLogger auctionLogger = AuctionLogger.getInstance();
        auctionLogger.log(LOG_FILE, this.message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
