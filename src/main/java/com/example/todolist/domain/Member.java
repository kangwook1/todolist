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
//아무런 매개변수가 없는 생성자를 생성하되 다른 패키지에 소속된 클래스는 접근을 불허한다 라는 뜻이다.
// 프록시 객체를 사용하기 위해서 JPA 구현체는, 실제 엔티티를 상속받아 만들어지기 때문에 사용하려면 private는 안된다.
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

    //영속성 전이는 one to many에만 가능(cascade), 따라서 양방향을 매핑하고 cascade를 쓸건지 단방향을 하고 수동으로 할건지 고민
    //수동으로 할때 onDelete 어노테이션으로 할 수 있다.
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Do> doList=new ArrayList<>();

    public void addUserAuthority(){
        this.role=Role.ROLE_USER;
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
