package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/form/v3/items")
@RequiredArgsConstructor
public class FormItemControllerV3 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        model.addAttribute("regions", regions);

        ItemType[] values = ItemType.values();
        model.addAttribute("itemTypes", values);

        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        model.addAttribute("deliveryCodes", deliveryCodes);
        // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end

        return "form/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());

        // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        model.addAttribute("regions",regions);

        ItemType[] values = ItemType.values();
        model.addAttribute("itemTypes", values);

        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        model.addAttribute("deliveryCodes", deliveryCodes);
        // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end

        return "form/v3/addForm";
    }


    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);

            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
            Map<String, String> regions = new LinkedHashMap<>();
            regions.put("SEOUL", "서울");
            regions.put("BUSAN", "부산");
            regions.put("JEJU", "제주");
            model.addAttribute("regions",regions);

            ItemType[] values = ItemType.values();
            model.addAttribute("itemTypes", values);

            List<DeliveryCode> deliveryCodes = new ArrayList<>();
            deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
            deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
            deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
            model.addAttribute("deliveryCodes", deliveryCodes);
            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end

            return "form/v3/addForm";
        }

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());
        Item savedItem = itemRepository.save(item);

        // 리다이렉트 주소 매개변수로 사용, status 는 쿼리로 들어감
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        model.addAttribute("regions",regions);

        ItemType[] values = ItemType.values();
        model.addAttribute("itemTypes", values);

        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        model.addAttribute("deliveryCodes", deliveryCodes);
        // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end

        return "form/v3/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @Validated @ModelAttribute Item item,
                       BindingResult bindingResult,
                       Model model) {


        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);

            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
            Map<String, String> regions = new LinkedHashMap<>();
            regions.put("SEOUL", "서울");
            regions.put("BUSAN", "부산");
            regions.put("JEJU", "제주");
            model.addAttribute("regions",regions);

            ItemType[] values = ItemType.values();
            model.addAttribute("itemTypes", values);

            List<DeliveryCode> deliveryCodes = new ArrayList<>();
            deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
            deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
            deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
            model.addAttribute("deliveryCodes", deliveryCodes);
            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end

            return "form/v3/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/form/v3/items/{itemId}";
    }

}

