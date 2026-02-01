package zaman.plugin.smp.data.shop;

public enum ShopTitle {
    FARM("§e농작물 판매");

    private final String name;

    ShopTitle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
