package io.movecloud.movecloud_login_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "ROLE_TYPE", nullable = false)
    private String roleType;

    @Column(name = "CRT_DT", updatable = false)
    private LocalDateTime crtDt;

    @Column(name = "MOD_DT")
    private LocalDateTime modDt;

    @Column(name = "CRT_BY")
    private Long crtBy;

    @Column(name = "MOD_BY")
    private Long modBy;


    @PrePersist
    protected void onCreate() {
        crtDt = LocalDateTime.now();
        modDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modDt = LocalDateTime.now();
    }
}
