package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.valueType.Address;
import jpabook.jpashop.domain.valueType.Period;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUserName("John Doe");

            Period period = new Period(LocalDateTime.now(), LocalDateTime.now().plusYears(1));
            member.setWordPeriod(period);

            Address homeAddress = new Address("서울", "강남구", "12345");
            member.setAddress(homeAddress);

            em.persist(member);

            // 1. find() 메서드로 기본키 조회
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("조회한 Member의 이름: " + findMember.getUserName());

            // 2.JPQL 쿼리로 전체 Member 조회
            List<Member> members = em.createQuery("select m from Member m ",  Member.class)
                                    .getResultList();

            System.out.println("전체 회원 수 : " + members.size());

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
