package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepositoy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.*;
import java.util.function.Function;


class MemberServiceTest {

    MemoryMemberRepositoy memberRepository;
    MemberService memberService;


    @BeforeEach
    public void beforeEcah(){
        memberRepository = new MemoryMemberRepositoy();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
        System.out.println("clear store");
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");
        //when
        Long saveId = memberService.join(member);



        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring");
        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void 이름으로_찾기(){
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        assertThat(memberService.findByName("spring1").get().getId()).isEqualTo(member1.getId());
    }

    @Test
    void 전체_회원_조회(){
        //before
        List<Member> members = memberService.findMembers();
        int bfCnt = members.size();

        System.out.println("기존 회원수 : "+bfCnt);

        //given
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);
        //when

        assertThat(memberService.findMembers().size()).isEqualTo(bfCnt+2);

    }

}