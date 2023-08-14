package Animal_House_BE.item;

public class Item {

    int itemId;
    String itemName;
    String itemDescription;
    String itemPicture_link;
    int itemPrice;
    int itemQuantity;

    public Item(int itemId, String itemName, String itemDescription, String itemPicture_link, int itemPrice, int itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPicture_link = itemPicture_link;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPicture_link() {
        return itemPicture_link;
    }

    public void setItemPicture_link(String itemPicture_link) {
        this.itemPicture_link = itemPicture_link;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
