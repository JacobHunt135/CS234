public class Item {

    private String name;
    private String location;
    private int stock;
    private int unitPrice;

    public Item(String name, String location, int stock, int unitPrice) {
        this.name = name;
        this.location = location;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public int getStock() {
        return stock;
    }
    public int getUPrice() {
        return unitPrice;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setUPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}