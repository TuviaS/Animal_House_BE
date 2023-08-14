package Animal_House_BE.item;

import java.util.List;

public interface ItemService {
    Item getItemByNumber(int itemNumber);

    int getItemPrice(int itemNumber);

    int getItemQuantity(int itemNumber);

    List<Item> getAllItems();

    void createItem(Item item);

    void reduceItemQunatity(int itemNumber);

    void increaseItemQunatity(int itemNumber);

    List<Item> getFavoriteItemsListByClientEmail(String email);

    void addToFavoriteItemList(String email, int itemId);

    void deleteItemFromFavoriteItemList(String email, int itemId);
}
