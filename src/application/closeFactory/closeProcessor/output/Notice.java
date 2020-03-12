package application.closeFactory.closeProcessor.output;

import services.PostOffice;

public class Notice extends Output {
    private String to;
    private String message;

    public Notice(String to, String message) {
        this.to = to;
        this.message = message;
    }

    @Override
    public void run() {
        PostOffice postOffice = PostOffice.getInstance();
        postOffice.sendEMail(this.to, this.message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
