package services;

public class AlwaysTrueOffHours implements Hours {
    @Override
    public Boolean isOffHours() {
        return true;
    }
}
