package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
// 현재 컨트롤러의 Base 경로를 지정
@RequestMapping("/basic/items")
// 모든 final로 정의한 사용자 장의 클래스를 자동으로 인스턴스
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

//    @GetMapping("/basic/items")  : 기본경로 미지정시
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
//    @PathVariable사용하는 경우 itemId는 request parameter의 itemId의 값으로 치환
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @GetMapping("/{itemId}edit")
    public String editform(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    input tage의 name 속성값과 일치하는 파라메터 매칭
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam Integer price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
//        레파지토리에 먼저 데이터를 저장한다.
        itemRepository.save(item);
//        저장과정에서 문제가 없는 경우에 화면에 값을 출력한다.
        model.addAttribute("item", item);
        return "basic/item";
    }
//    @ModelAttribute() 속성명을 생략하면 매개변수 이름으로 addAttribute메소드의 속성과 값을
//    내부적으로 자동으로 생성해 준다.
//@PostMapping("add")
public String addItemV3(@ModelAttribute Item item, Model model) {
        itemRepository.save(item);
        return "basic/item";
}
//     Post방식에서 폼데이터가 전송될 경우
//     @ModelAttribute, Model 도 생략 가능하다.
    @PostMapping("add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

//    프로젝트 구동후 스프링빈이 등록되고 모든 의존관계가 주입이 되고 난 이후 수행
//    아래 예제에는 테스트용 데이터를 넣기 위해 사용
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("ItemA", 10000, 10));
        itemRepository.save(new Item("ItemB", 20000, 20));
    }
}
