package application.commission;

public class CarShipCommission extends Commission{
    public CarShipCommission(Integer nowPrice) {
        super(nowPrice);
    }

    @Override
    public Integer getCommission() {
        return  this.getNowPrice() + 1000 ;
    }
}
