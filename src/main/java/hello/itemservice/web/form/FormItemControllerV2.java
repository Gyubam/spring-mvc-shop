package hello.itemservice.web.form;

import hello.itemservice.domain.item.DeliveryCode;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/form/v2/items")
@RequiredArgsConstructor
public class FormItemControllerV2 {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/v2/items";
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

        return "form/v2/item";
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

        return "form/v2/addForm";
    }

////    @PostMapping("/add")
//    public String addItemV1(@ModelAttribute Item item,
//                          BindingResult bindingResult,
//                          RedirectAttributes redirectAttributes,
//                          Model model) {
//
//        //검증 오류 결과를 보관
//        Map<String, String> errors = new HashMap<>();
//
//        //검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", "가격은 1,000원 ~ 1,000,000 까지 허용합니다."));
//        }
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999까지 허용합니다."));
//        }
//
//        //특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
//            }
//        }
//
//        //검증에 실패하면 다시 입력 폼으로 이동
//        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = {}", bindingResult);
//
//            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
//            Map<String, String> regions = new LinkedHashMap<>();
//            regions.put("SEOUL", "서울");
//            regions.put("BUSAN", "부산");
//            regions.put("JEJU", "제주");
//            model.addAttribute("regions",regions);
//
//            ItemType[] values = ItemType.values();
//            model.addAttribute("itemTypes", values);
//
//            List<DeliveryCode> deliveryCodes = new ArrayList<>();
//            deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
//            deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
//            deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
//            model.addAttribute("deliveryCodes", deliveryCodes);
//            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end
//
//            return "form/v2/addForm";
//        }
//
//        log.info("item.open={}", item.getOpen());
//        log.info("item.regions={}", item.getRegions());
//        log.info("item.itemType={}", item.getItemType());
//        Item savedItem = itemRepository.save(item);
//
//        // 리다이렉트 주소 매개변수로 사용, status 는 쿼리로 들어감
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/form/v2/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV2(@ModelAttribute Item item,
//                            BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes,
//                            Model model) {
//
//        //검증 오류 결과를 보관
//        Map<String, String> errors = new HashMap<>();
//
//        //검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수입니다."));
//
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000원 ~ 1,000,000 까지 허용합니다."));
//        }
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999까지 허용합니다."));
//        }
//
//        //특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", null, null, "가격 * 수량의 합은 10,000원 이상이어야 합니다."));
//            }
//        }
//
//        //검증에 실패하면 다시 입력 폼으로 이동
//        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = {}", bindingResult);
//
//            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
//            Map<String, String> regions = new LinkedHashMap<>();
//            regions.put("SEOUL", "서울");
//            regions.put("BUSAN", "부산");
//            regions.put("JEJU", "제주");
//            model.addAttribute("regions",regions);
//
//            ItemType[] values = ItemType.values();
//            model.addAttribute("itemTypes", values);
//
//            List<DeliveryCode> deliveryCodes = new ArrayList<>();
//            deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
//            deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
//            deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
//            model.addAttribute("deliveryCodes", deliveryCodes);
//            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end
//
//            return "form/v2/addForm";
//        }
//
//        log.info("item.open={}", item.getOpen());
//        log.info("item.regions={}", item.getRegions());
//        log.info("item.itemType={}", item.getItemType());
//        Item savedItem = itemRepository.save(item);
//
//        // 리다이렉트 주소 매개변수로 사용, status 는 쿼리로 들어감
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/form/v2/items/{itemId}";
//    }

//    @PostMapping("/add")
//    public String addItemV3(@ModelAttribute Item item,
//                            BindingResult bindingResult,
//                            RedirectAttributes redirectAttributes,
//                            Model model) {
//
//        //검증 오류 결과를 보관
//        Map<String, String> errors = new HashMap<>();
//
//        //검증 로직
//        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
//
//        }
//        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
//        }
//        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
//        }
//
//        //특정 필드가 아닌 복합 룰 검증
//        if (item.getPrice() != null && item.getQuantity() != null) {
//            int resultPrice = item.getPrice() * item.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
//            }
//        }
//
//        //검증에 실패하면 다시 입력 폼으로 이동
//        if (bindingResult.hasErrors()) {
//            log.info("bindingResult = {}", bindingResult);
//
//            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- start
//            Map<String, String> regions = new LinkedHashMap<>();
//            regions.put("SEOUL", "서울");
//            regions.put("BUSAN", "부산");
//            regions.put("JEJU", "제주");
//            model.addAttribute("regions",regions);
//
//            ItemType[] values = ItemType.values();
//            model.addAttribute("itemTypes", values);
//
//            List<DeliveryCode> deliveryCodes = new ArrayList<>();
//            deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
//            deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
//            deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
//            model.addAttribute("deliveryCodes", deliveryCodes);
//            // 체크박스, 라디오버튼, 텍스트상자 설정 코드 -- end
//
//            return "form/v2/addForm";
//        }
//
//        log.info("item.open={}", item.getOpen());
//        log.info("item.regions={}", item.getRegions());
//        log.info("item.itemType={}", item.getItemType());
//        Item savedItem = itemRepository.save(item);
//
//        // 리다이렉트 주소 매개변수로 사용, status 는 쿼리로 들어감
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("status", true);
//        return "redirect:/form/v2/items/{itemId}";
//    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

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

            return "form/v2/addForm";
        }

        log.info("item.open={}", item.getOpen());
        log.info("item.regions={}", item.getRegions());
        log.info("item.itemType={}", item.getItemType());
        Item savedItem = itemRepository.save(item);

        // 리다이렉트 주소 매개변수로 사용, status 는 쿼리로 들어감
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/v2/items/{itemId}";
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

        return "form/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/v2/items/{itemId}";
    }

}

