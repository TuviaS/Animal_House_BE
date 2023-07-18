package Animal_House_BE.item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/item/{itemNumber}")
    public Item getItemByNumber(@PathVariable int itemNumber) {
        return itemService.getItemByNumber(itemNumber);
    }
}
