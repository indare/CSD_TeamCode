package application.commission;

import static application.parameters.Parameters.DELIVERY_COST;

public class EtcCommission extends Commission {
    public EtcCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return this.getNowPrice() + DELIVERY_COST;
    }
}
