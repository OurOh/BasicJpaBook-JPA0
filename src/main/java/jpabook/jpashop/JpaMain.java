package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 테스트 데이터 추가
            Member member1 = new Member();
            member1.setUserName("John");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUserName("Alice");
            em.persist(member2);

            Member member3 = new Member();
            member3.setUserName("Bob");
            em.persist(member3);

            em.flush();  // DB에 반영
            em.clear();  // 영속성 컨텍스트 초기화

            // JPQL 실행
            List<String> userNames = em.createQuery("SELECT m.userName FROM Member m", String.class)
                    .getResultList();

            // 결과 출력
            for (String name : userNames) {
                System.out.println("회원 이름: " + name);
            }

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
