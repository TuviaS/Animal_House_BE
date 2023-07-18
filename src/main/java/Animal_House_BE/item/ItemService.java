package Animal_House_BE.item;

public interface ItemService {
    Item getItemByNumber (int itemNumber);
    int getItemPrice(int itemNumber);
    int getItemQuantity (int itemNumber);

}
