package com.sparta.memo;

import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {

    // 스프링부트에서 자동으로 생성해주는 엔티티매니저를 주입받아 올때 사용
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemoRepository memoRepository;

    @Test
    @Transactional
    @Rollback(value = false) // 테스트 코드에서 @Transactional 를 사용하면 테스트가 완료된 후 롤백하기 때문에 false 옵션 추가
    @DisplayName("메모 생성 성공")
    void test1() {
        Memo memo = new Memo();
        memo.setUsername("Robbert");
        memo.setContents("@Transactional 테스트 중!");

        em.persist(memo);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
    }

    @Test
    @Disabled // 테스트를 수행하지 않겠다
    @DisplayName("메모 생성 실패")
    void test2() {
        Memo memo = new Memo();
        memo.setUsername("Robbie");
        memo.setContents("@Transactional 테스트 중!");

        em.persist(memo);  // 영속성 컨텍스트에 메모 Entity 객체를 저장합니다.
        // No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
        // 트랜잭션을 걸어주지 않으면 오류
    }

    @Test
    @Disabled // 나중에 MemoRepository를 지우기 때문에 비활성화
    @Transactional(propagation = Propagation.REQUIRED) // Default 값
    @Rollback(value = false)
    @DisplayName("트랜잭션 전파 테스트")
    void test3() {
//        memoRepository.createMemo(em);
        System.out.println("테스트 test3 메서드 종료");
        // 부모 Tx와 자식 Tx가 둘 다 있으면 이어진다.
    }
}
