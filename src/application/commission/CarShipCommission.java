package application.commission;

import static application.parameters.Parameters.CAR_DELIVERY_COST;

public class CarShipCommission extends Commission{
    public CarShipCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return  this.getNowPrice() + CAR_DELIVERY_COST ;
    }
}
