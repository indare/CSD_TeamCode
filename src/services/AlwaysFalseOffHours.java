package services;

public class AlwaysFalseOffHours implements Hours {
    @Override
    public Boolean isOffHours() {
        return false;
    }
}
