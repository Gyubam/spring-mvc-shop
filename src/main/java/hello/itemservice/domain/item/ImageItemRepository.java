package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ImageItemRepository {

    private final Map<Long, ImageItem> store = new HashMap<>();
    private long sequence = 0L;

    public ImageItem save(ImageItem item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public ImageItem findById(Long id) {
        return store.get(id);
    }
}
