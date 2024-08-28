package io.movecloud.movecloud_login_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "BUSINESS_EMAIL", nullable = false)
    private String businessEmail;

    @Column(name = "ENCRYPTED_PASSWORD", nullable = false)
    private String encryptedPassword;

    @Column(name = "COMPANY_NAME", nullable = false)
    private String companyName;

    @Column(name = "MOBILE_NUMBER", nullable = false)
    private Long mobileNumber;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID", nullable = false)
    private Role role;

    @Column(name = "AWS_ACCESS_KEY", nullable = true)
    private String awsAccessKey;

    @Column(name = "AWS_SECRET_ACCESS_KEY", nullable = true)
    private String awsSecretAccessKey;

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
