package zaman.plugin.smp.data.dto;

public class PlayerData {
    private String name;
    private int cash;
    private int coin;
    private int orb;
    private String lastAttendance;

    public PlayerData(String name, int cash, int coin, int orb, String lastAttendance) {
        this.name = name;
        this.cash = cash;
        this.coin = coin;
        this.orb = orb;
        this.lastAttendance = lastAttendance;
    }

    public String getName() {
        return name;
    }

    public int getCash() {
        return cash;
    }

    public int getCoin() {
        return coin;
    }

    public int getOrb() {
        return orb;
    }

    public String getLastAttendance() {
        return lastAttendance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setOrb(int orb) {
        this.orb = orb;
    }

    public void setLastAttendance(String lastAttendance) {
        this.lastAttendance = lastAttendance;
    }
}
