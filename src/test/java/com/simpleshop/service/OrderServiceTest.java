package com.simpleshop.service;

import com.simpleshop.domain.Address;
import com.simpleshop.domain.Member;
import com.simpleshop.domain.Order;
import com.simpleshop.domain.OrderStatus;
import com.simpleshop.domain.item.Book;
import com.simpleshop.domain.item.Item;
import com.simpleshop.repository.MemberRepository;
import com.simpleshop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        Member member = createMember();
        Item item = createItem("test", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order getOrder = orderRepository.findById(orderId).get();
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus());
        Assertions.assertEquals(1, getOrder.getOrderItems().size());
        Assertions.assertEquals(10000 * 2, getOrder.getTotalPrice());
        Assertions.assertEquals(8, item.getStockQuantity());
    }

    private Member createMember() {
        Member member = Member.builder()
                .name("회원1")
                .address(new Address("서울", "강가", "123-123"))
                .build();
        memberService.join(member);
        return member;
    }

    private Book createItem(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .stockQuantity(stockQuantity)
                .price(price)
                .build();
        itemService.saveItem(book);
        return book;
    }
}