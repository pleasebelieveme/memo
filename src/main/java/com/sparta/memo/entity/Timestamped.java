package com.sparta.memo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속받으면 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing 기능을 포함시켜라
public abstract class Timestamped { // 이 클래스만 독자적으로 사용할 일이 없기에 추상클래스로 지정

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP) // Date, Calender 데이터 매핑할 때 사용
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}