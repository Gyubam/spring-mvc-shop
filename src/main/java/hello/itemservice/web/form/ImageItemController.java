package hello.itemservice.web.form;

import hello.itemservice.domain.item.ImageItem;
import hello.itemservice.domain.item.ImageItemRepository;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.UploadFile;
import hello.itemservice.file.FileStore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UriUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageItemController {

    private final ImageItemRepository imageItemRepository;
    private final FileStore fileStore;

    @GetMapping("/hello/items/new")
    public String newItem(@ModelAttribute ImageItemForm form) {
        return "item-form";
    }

    @PostMapping("/hello/items/new")
    public String saveItem(@ModelAttribute ImageItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        List<MultipartFile> imageFiles = form.getImageFiles();
        List<UploadFile> storeImageFiles = fileStore.storeFiles(imageFiles);

        //데이터베이스에 저장
        ImageItem item = new ImageItem();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        imageItemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/hello/items/{itemId}";
    }

    @GetMapping("hello/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        ImageItem item = imageItemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";

    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        log.info("fileStore.getFurllPath={}", fileStore.getFullPath(filename));
        return new UrlResource("file:" + fileStore.getFullPath(filename));

    }

    @GetMapping("/hello/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        ImageItem item = imageItemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        //깨질수 있으므로 인코딩된 파일명 사용
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);


    }
}
