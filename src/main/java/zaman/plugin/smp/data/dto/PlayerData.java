package zaman.plugin.smp.data.dto;

public class PlayerData {
    private String name;
    private int coin;
    private int gem;
    private String lastAttendance;

    public PlayerData(String name, int coin, int gem, String lastAttendance) {
        this.name = name;
        this.coin = coin;
        this.gem = gem;
        this.lastAttendance = lastAttendance;
    }

    public String getName() {
        return name;
    }

    public int getCoin() {
        return coin;
    }

    public int getGem() {
        return gem;
    }

    public String getLastAttendance() {
        return lastAttendance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setGem(int gem) {
        this.gem = gem;
    }

    public void setLastAttendance(String lastAttendance) {
        this.lastAttendance = lastAttendance;
    }
}
