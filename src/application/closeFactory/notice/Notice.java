package application.closeFactory.notice;

import services.PostOffice;

public class Notice {
    private String to;
    private String message;

    public Notice(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public void send() {
        PostOffice postOffice = PostOffice.getInstance();
        postOffice.sendEMail(this.to, this.message);
    }

    public String getMessage() {
        return this.message;
    }
}
