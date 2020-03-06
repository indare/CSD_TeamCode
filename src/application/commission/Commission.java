package application.commission;

public abstract class Commission {
    private Integer nowPrice;
    public Commission(Integer nowPrice){
        this.nowPrice = nowPrice;
    }
    public abstract Integer getCommission();
    protected Integer getNowPrice() {
        return this.nowPrice;
    }
}
