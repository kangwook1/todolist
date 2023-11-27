package com.example.todolist.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false,unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Do> doList=new ArrayList<>();

    public void addUserAuthority(){
        this.role=Role.Role_USER;
    }
    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password=passwordEncoder.encode(password);
    }
    @Builder
    public Member(String loginId,String password, String email, String nickname) {
        this.loginId=loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    //grantedAuthority는 부여된 권한을 갖는 인터페이스,SimpleGrantedAuthority는 granteAuthority를 구현한 간단한 클래스
    //"ROLE_USER", "ROLE_ADMIN"과 같은 권한을 나타내는 문자열을 SimpleGrantedAuthority 객체로 생성하여 사용할 수 있다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auth=new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(role.name()));
        return auth;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
