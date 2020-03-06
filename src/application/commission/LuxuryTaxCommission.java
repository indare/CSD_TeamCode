package application.commission;

import static application.parameters.Parameters.LUXURY_TAX_RATE;

public class LuxuryTaxCommission extends Commission{
    public LuxuryTaxCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return  (int) (this.getNowPrice() * LUXURY_TAX_RATE) ;
    }
}
