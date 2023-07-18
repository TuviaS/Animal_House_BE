package Animal_House_BE.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class itemServiceImplement implements ItemService{
    @Autowired
    ItemRepository itemRepository;
    @Override
    public Item getItemByNumber(int itemNumber) {
        return itemRepository.getItemByNumber (itemNumber);

    }

    @Override
    public int getItemPrice(int itenNumber) {
        return itemRepository.getItemPrice(itenNumber);
    }

    @Override
    public int getItemQuantity(int itemNumber) {
        return itemRepository.getItemQuantity(itemNumber);
    }
}
