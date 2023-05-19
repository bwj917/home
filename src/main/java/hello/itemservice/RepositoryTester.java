package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
// Repository 구현객체를 검증하기 위한 전용 테스트 프로그램 => 테스트 드라이버
// 스프링 프로젝트에서 전문적으로 테스트를 지원하는 프레임워크 => JUnit
@SpringBootApplication
public class RepositoryTester {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RepositoryTester.class);
        app.setWebApplicationType(WebApplicationType.NONE); // 웹 애플리케이션 모드를 변경
        ApplicationContext context = app.run(args);
        ItemRepository itemRepository = new ItemRepository(); // Ctrl + Alt + v   // new ItemRepository

        System.out.println("\nRepositoryTester.main] ItemRepository Test");

        saveTester(itemRepository);
        // 이전 테스트에서 설정된 데이터를 모두 삭제
        // 각 테스트 마다 독립적인 데이터 셋으로 테스트하기 위해 clearStore 메소드 실행
        itemRepository.clearStore();

        findAllTester(itemRepository);
        itemRepository.clearStore();

        updateTester(itemRepository);


        SpringApplication.exit(context); // 애플리케이션 종료
    }

    private static void updateTester(ItemRepository itemRepository) {
        System.out.println("\nRepositoryTester.updateTester] 구동");
        Item itemA = new Item("ItemA", 10000, 10);
        Item itemSaved = itemRepository.save(itemA);
        System.out.println("수정전] itemSaver = " + itemSaved);

        Item itemUpdateParam = new Item("ItemB", 15000, 20);
        itemRepository.update(itemSaved.getId(), itemUpdateParam);

        Item itemFinded = itemRepository.findById(itemSaved.getId());
        System.out.println("수정후] itemFinded = " + itemFinded);
    }


    private static void findAllTester(ItemRepository itemRepository) {
        System.out.println("\nRepositoryTester.findAllTester] 구동");
        Item item1 = new Item("Item1", 10000, 10);
        itemRepository.save(item1);
        Item item2 = new Item("Item2", 1000, 100);
        itemRepository.save(item2);

        List<Item> allFinded = itemRepository.findAll();
        System.out.println("allFinded = " + allFinded);
    }

    private static void saveTester(ItemRepository itemRepository) {
        System.out.println("\nRepositoryTester.saveTester] 구동");
        Item itemA = new Item("ItemA", 10000, 10);
        Item itemSaved = itemRepository.save(itemA);
        Item itemFinded = itemRepository.findById(itemSaved.getId());
        System.out.println("itemFinded = " + itemFinded);
    }
}
