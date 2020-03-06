package application.commission;

public class EtcCommission extends Commission {
    public EtcCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return this.getNowPrice() + 10;
    }
}
