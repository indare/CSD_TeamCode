package application.commission;

public class LuxuryTaxCommission extends Commission{
    public LuxuryTaxCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return  (int) (this.getNowPrice() * 1.04) ;
    }
}
