package kiosk;

import java.util.*;

public class Setting {
    Scanner sc = new Scanner(System.in);
    private Map<Integer, String> menu_num = new HashMap<>();
    private ArrayList<Menu> menu_list = new ArrayList<Menu>();
    private ArrayList<Food> food_list = new ArrayList<Food>();
    private HashMap<String, Double> food_price_map = new HashMap<String, Double>();

    private HashMap<String, Integer> food_count_map = new HashMap<String, Integer>();

    private boolean option_flg = false;

    public void loadMenu(){
    Menu  buger = new Menu("Burgers", "앵거스 비프 통살을 다져만든 버거");
    Menu  SIDE = new Menu("Sides", "매장에서 직접 튀기는 감자튀김");
    Menu  Drinks = new Menu("Drinks", "매장에서 직접 만드는 음료");

    menu_list.add(buger);
    menu_list.add(SIDE);
    menu_list.add(Drinks);

    Food shack_burger = new Food("Burgers", "shack_burger", 6.9,"토마토, 양상추, 쉑소스가 토핑된 치즈버거");
    Food SmokeShack = new Food("Burgers", "SmokeShack", 8.9,"베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거");
    Food ShroomBurger = new Food("Burgers", "Shroom Burger", 9.4,"몬스터 치즈와 체다 치즈로 속을 채운 베지테리안 버거");
    Food CheeseBurger = new Food("Burgers", "Cheese Burger", 6.9,"포테이토 번과 비프패티, 치즈가 토핑된 치즈버거");
    Food Hamburger = new Food("Burgers", "Hamburger", 5.4,"비프패티를 기반으로 야채가 들어간 기본버거");
    food_list.add(shack_burger);
    food_list.add(SmokeShack);
    food_list.add(ShroomBurger);
    food_list.add(CheeseBurger);
    food_list.add(Hamburger);

    Food cherry_papper_cheese = new Food("Sides", "Cherry Papper Cheese", 5.4,"매콤한 체리 페퍼와 고소한 치즈 소스가 어우러진 프라이");
    food_list.add(cherry_papper_cheese);

    Food shackmadeLemonade = new Food("Drinks","Shack-made Lemonade",  3.9,"매장에서 직접 만드는 상큼한 레몬에이드");
    Food freshBrewedIcedTea = new Food("Drinks","Fresh Brewed Iced Tea", 3.4,"직접 유기농 홍차를 우려낸 아이스티" );
    Food bottledWater = new Food("Drinks","Bottled Water", 1.0,"지리산 암반대수층으로 만든 프리미엄 생수");
    food_list.add(shackmadeLemonade);
    food_list.add(freshBrewedIcedTea);
    food_list.add(bottledWater);
    }

    //메인 메뉴판 화면
    public void  display() throws InterruptedException {

        try {
            int cnt = showMenu();
            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();


            // 총 판매상품 목록
            if (num == 0) {
                AllfoodScreen();

            }

            if (num <= menu_list.size()) {
                // 상품 메뉴판 화면
                String menu_name = menu_num.get(num);
                showDetail(menu_name);
            } else if (num == menu_list.size() + 1) {
                // 주문 화면
                ReceiptScreen();
            } else if (num == menu_list.size() + 2) {
                // 취소 화면
                calcelScreen();
            } else {
                System.out.println("범위 입력 오류, 재 입력 해주세요.\n");
                display();
            }
        }  catch (InputMismatchException e){
            System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.\n");
        }
    }

    // 초기메뉴 표시
    public int showMenu(){

        int cnt = 1;
        System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.");
        System.out.println("[ SHAKESHACK MENU ]");
        for (Menu i : menu_list){
            menu_num.put(cnt, i.getName());
            System.out.print(cnt + ". ");
            i.ShowMenu();
            cnt++;
        }
        System.out.println();
        System.out.println("[ ORDER MENU ]");
        System.out.println(cnt + ". "+ String.format("%-15s", "Order")+" | "+"장바구니를 확인 후 주문합니다.");
        System.out.println((cnt +1) + ". "+ String.format("%-15s", "Cancel")+" | "+"진행중인 주문을 취소합니다.");
        return  cnt;
    }

    /**
     * 세부 메뉴표시
     * @param menu_name 표시할 메뉴 이름
     */
    public void showDetail(String menu_name) throws InterruptedException {

        HashMap<Integer, ArrayList<String>> order_map = new HashMap<Integer, ArrayList<String>>();
        Map<Integer, Double> PriceMap = new HashMap<>();

        int cnt = 1;
        System.out.println("SHAKESHACK BURGER 에 오신걸 환영합니다.");
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.");
        System.out.printf("[ %s MENU ]\n",menu_name);

        // 옵션 메뉴일시 옵션메뉴 표시를 위해 플래그 체크
        if (Objects.equals(menu_name, "Burgers") || Objects.equals(menu_name, "Drinks")){
            option_flg = true;
        }

        for (Food i : food_list){
            if(Objects.equals(i.getType(),menu_name)) {
                if (i.getOption1_name() != null){
                    break; // 옵션으로 추가된 메뉴는 표시 하지않음
                }
                ArrayList<String> list = new ArrayList<String>();
                list.add(i.getName()); // 이름 추가
                list.add(i.getMenu()); // 메뉴표시 추가

                order_map.put(cnt,list); // {주문번호:[주문이름,주문메뉴표시]}
                PriceMap.put(cnt,i.getPrice()); // 주문번호와 가격
                System.out.print(cnt + ". ");
                i.ShowMenu();
                cnt++;
            }
        }
        try{int FoodNum = sc.nextInt();
        if (FoodNum >= cnt || FoodNum == 0){
            System.out.println("범위 입력 오류, 재 입력 해주세요. \n");
            showDetail(menu_name);
        }
        double food_price = PriceMap.get(FoodNum); //선택한 주문에 해당하는 가격
        // 구매 화면
        OrderScreen(FoodNum, order_map, food_price);}
        catch (InputMismatchException e){
            System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.\n");
        }
    }

    /**
     * 주문선택시 메뉴표시
     * @param FoodNum 주문한 메뉴 번호
     * @param order_map {주문번호:[주문이름,주문메뉴표시]}
     * @param food_price 선택 주문 음식 가격
     */
    public void  OrderScreen(int FoodNum, HashMap<Integer, ArrayList<String>> order_map, double food_price) throws InterruptedException {
        try {
            String OrderFood = order_map.get(FoodNum).get(1); // 주문 텍스트
            String order_name = order_map.get(FoodNum).get(0); // 주문이름
            System.out.println(OrderFood);
            //옵션화면
            if (option_flg) {
                Food option_food = OpstionScreen(order_name);
                order_name = option_food.getName();
                food_price = option_food.getPrice();
            }

            System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
            System.out.println("1. 확인        2. 취소");

            int order_num = sc.nextInt();

            if (order_num == 1) {
                System.out.printf(" %s 가 장바구니에 추가되었습니다.\n", order_name);

                food_price_map.put(order_name, food_price);

                if (food_count_map.containsKey(order_name)) {
                    food_count_map.put(order_name, food_count_map.get(order_name) + 1);
                } else {
                    food_count_map.put(order_name, 1);
                }
                display();

            } else if (order_num == 2) {
                System.out.println("진행하던 주문이 취소되었습니다.\n");
                display();

            } else {
                System.out.println("범위 입력 오류, 재 입력 해주세요. \n");
                OrderScreen(FoodNum, order_map, food_price);
            }

        }
        catch (InputMismatchException e){
            System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.\n");
        }
    }

    // Order 화면 표시
    public void  ReceiptScreen() throws InterruptedException {
        try {

            int wait = 1;
            double total = 0;

            System.out.println("아래와 같이 주문 하시겠습니까?");
            System.out.println("[ Orders ]");
            for (Food i : food_list) {
                //주문 개수 기능 추가 food_count_map {주문이름: 갯수}
                if (food_count_map.containsKey(i.getName())) {
                    System.out.println(i.getMenu(food_count_map.get(i.getName())));
                    total += i.getPrice() * food_count_map.get(i.getName());
                }
            }

            System.out.println("[ Total ]");
            System.out.println(String.format("W %.1f",total));
            System.out.println("1. 주문      2. 메뉴판");

            int order_num = sc.nextInt();
            if (order_num == 1) {
                // 주문내역이 비었을시 초기화면으로
                if(food_count_map.keySet().size()==0){
                    System.out.println(" 주문 내역이 없습니다. \n");
                    display();
                }
                System.out.println("주문이 완료되었습니다! ");
                System.out.println(String.format("대기번호는 [%d] 번 입니다.", wait));
                System.out.println("(3초후 메뉴판으로 돌아갑니다.) \n");

                // 장바구니 초기화
                food_count_map.clear();
                Thread.sleep(3000);

            } else if (order_num == 2) {
                System.out.println(" 메뉴판으로 돌아갑니다\n");
                display();
            } else {
                System.out.println("범위 입력 오류, 재 입력 해주세요.\n");
                display();
            }
        }catch (InputMismatchException e){
            System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.\n");
        }


    }
    // Cancel 화면 표시
    public void  calcelScreen() throws InterruptedException {
        try {
            System.out.println("진행하던 주문을 취소하시겠습니까?");
            System.out.println("1. 확인        2. 취소");

            int order_num = sc.nextInt();
            if (order_num == 1) {
                System.out.println("진행하던 주문이 취소되었습니다.");
                // 장바구니 초기화
                food_count_map.clear();

            } else if (order_num == 2) {
                System.out.println(" 메뉴판으로 돌아갑니다\n");
                display();
            } else {
                System.out.println("범위 입력 오류, 재 입력 해주세요.\n");
                display();
            }
        }catch (InputMismatchException e){
            System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.\n");
        }
    }
    //초기메뉴에서  0번 선택시 총 판매금액&목록 조회 화면
    public void  AllfoodScreen() throws InterruptedException {

        System.out.println("[ 총 판매금액 현황 ]");
        System.out.println("현재까지 총 판매된 상품 목록은 아래와 같습니다.");
        // 판매 상품 목록 표시
        for (String key : food_price_map.keySet()){
            System.out.printf("- %-20s | W %f\n",key,food_price_map.get(key));
        }

        // 총 판맥 상품액 표시
        double sum = 0;
        for ( String i : food_count_map.keySet()){
            sum += food_price_map.get(i) * food_count_map.get(i);
        }
        System.out.printf("현재까지 총 판매된 금액은 [ W  %f ] 입니다.\n", sum);
        System.out.println();

        System.out.println("1. 돌아가기");
        try {
            int num = sc.nextInt();

            if (num == 1) {
                display();
            } else {
                System.out.println("범위 입력 오류, 재 입력 해주세요.\n");
                AllfoodScreen();
            }
        }catch (InputMismatchException e){
            System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.\n");
        }


    }


    /**
     * 주문시 옵션선택 화면
     * option_flg가 true인경우에만 동작
     * @param order_name 주문 음식 이름
     * @return option_food 추가 옵션 메뉴
     */
    public Food  OpstionScreen(String order_name) {

            option_flg = false;

            ArrayList<String> add_list = new ArrayList<String>();


            System.out.println("위 메뉴의 어떤 옵션으로 추가하시겠습니까?");
            Food option_food = null;
            for (Food i : food_list) {
                if (Objects.equals(i.getName(), order_name)) {
                    option_food = i.copyFood(i); // 기존메뉴에서 이름과 가격을 바꾸기위해 복사
                }
            }
            // 메뉴에 맞는 옵션추가, 표시
            switch (option_food.getType()) {
                case "Burgers":
                    option_food.setOption1_name("Single");
                    option_food.setOption1_price(5.4);

                    option_food.setOption2_name("Double");
                    option_food.setOption2_price(9.0);
                    break;
                case "Drinks":
                    option_food.setOption1_name("Double Shot");
                    option_food.setOption1_price(3.0);

                    option_food.setOption2_name("Cool");
                    option_food.setOption2_price(2.0);
                    break;
                default:
                    break;
            }

            System.out.printf("1. %s(W %.1f)        2. %s(W %.1f)\n", option_food.getOption1_name(), option_food.getOption1_price(), option_food.getOption2_name(), option_food.getOption2_price());
            try{
            int option_num = sc.nextInt();

            // 입력받은 옵션에 맞게 이름, 가격 변경
            if (option_num == 1) {
                option_food.setName(option_food.getName() + String.format("(%s)", option_food.getOption1_name()));
                option_food.setPrice(option_food.getPrice() + option_food.getOption1_price());
            } else if (option_num == 2) {
                option_food.setName(option_food.getName() + String.format("(%s)", option_food.getOption2_name()));
                option_food.setPrice(option_food.getPrice() + option_food.getOption2_price());
            } else {
                System.out.println("범위 입력 오류, 재 입력 해주세요. \n");
                OpstionScreen(order_name);
            }}catch (InputMismatchException e){
                System.out.println("잘못된 형식입니다. 숫자만 입력해 주세요. 프로그램이 종료됩니다.");
            }

            option_food.ShowMenu();

            // 리스트 컴플리헨션.. 없낭 나중에 찾기
            // 세부메뉴리스트에서 이름만 찾아 리스트로 만듬
            for (Food i : food_list) {
                add_list.add(i.getName());
            }
            // 위에서 만든 이름리스트에 추가된 옵션주문이 없다면 주문개수, 주문내역 표시등을 위해 추가
            if (!(add_list.contains(option_food.getName()))) {
                food_list.add(option_food);
            }

            return option_food;
        }

}
