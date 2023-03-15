package com.simpleshop.service;

import com.simpleshop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception {

        Member member = Member.builder().name("testName").build();

        Long saveId = memberService.join(member);

        Assertions.assertThat(member.getId()).isEqualTo(saveId);
    }

    @Test
    public void 중복회원예외() throws Exception {
        Member member1 = Member.builder().name("testName").build();
        Member member2 = Member.builder().name("testName").build();

        memberService.join(member1);

        IllegalStateException thrown = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
        org.junit.jupiter.api.Assertions.assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }

}