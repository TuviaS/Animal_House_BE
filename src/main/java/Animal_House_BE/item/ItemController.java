package Animal_House_BE.item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"},
        methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS})
public class ItemController {

    @Autowired
    private ItemService itemService;


    @CrossOrigin(origins = {"http://localhost:3000"},
            methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS})


    @GetMapping(value = "/item/{itemNumber}")
    public Item getItemByNumber(@PathVariable int itemNumber) {
        return itemService.getItemByNumber(itemNumber);
    }

    @GetMapping(value = "/items/getAllItems")
    public List<Item> fetAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping(value = "/item/create")
    public void createItem(@RequestBody Item item) {
        itemService.createItem(item);
    }

    @PutMapping(value = "/item/reduce/{itemNumber}")
    public void reduceItemQunatity(@PathVariable int itemNumber) {
        itemService.reduceItemQunatity(itemNumber);
    }

    @PutMapping(value = "/item/increase/{itemNumber}")
    public void increaseItemQuantity(@PathVariable int itemNumber) {
        itemService.increaseItemQunatity(itemNumber);
    }

    // FAVOURITE ITEMS CRUD

    @GetMapping(value = "/item/getFavoriteItemsList/{email}")
    public List<Item> getFavoriteItemsListByClientEmail(@PathVariable String email) {
        return itemService.getFavoriteItemsListByClientEmail(email);
    }

    @PutMapping(value = "/item/AddToFavoriteItemList/{email}")
    public void addToFavoriteItemList(@PathVariable String email, @RequestBody int itemId) {
        itemService.addToFavoriteItemList(email, itemId);
    }

    @PutMapping(value = "/item/deleteItemFromFavoriteItemList/{email}")
    public void deleteItemFromFavoriteItemList(@PathVariable String email, @RequestBody int itemId) {
        itemService.deleteItemFromFavoriteItemList(email, itemId);
    }
}
