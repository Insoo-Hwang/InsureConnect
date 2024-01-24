package com.example.InsureConnect.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultRequest {
    @Id
    @GeneratedValue
    private Long id;

    private Long plannerId;

    private String userNickname;

    private UUID chatRoomId;

    private int status;

    private Timestamp requestTime;

    public void setRequestTime() {
        this.requestTime = new Timestamp(System.currentTimeMillis());
    }

}
