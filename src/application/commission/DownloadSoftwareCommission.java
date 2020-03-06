package application.commission;

public class DownloadSoftwareCommission extends Commission{
    public DownloadSoftwareCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return this.getNowPrice();
    }
}
