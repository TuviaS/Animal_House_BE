package Animal_House_BE.item;

import java.util.List;

public interface ItemRepository {
    Item getItemByNumber(int itemNumber);

    int getItemPrice(int itemNumber);

    int getItemQuantity(int itemNumber);

    List<Item> getAllItems();

    void createItem(Item item);

    void reduceItemQunatity(int itemNumber);

    void increaseItemQunatity(int itemNumber);

    List<Item> getFavoriteItemsListByClientId(int clientId);

    void addToFavoriteItemList(int clientId, int itemId);

    void deleteItemFromFavoriteItemList(int clientId, int itemId);
}
