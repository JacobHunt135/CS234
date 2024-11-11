public class Item {

    private String name;
    private String location;
    private int stock;
    private int unitPrice;

    //Constructor for a new item.
    public Item(String name, String location, int stock, int unitPrice) {
        this.name = name;
        this.location = location;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }

    //Getters for an item.
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

    //Setters for an item.
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