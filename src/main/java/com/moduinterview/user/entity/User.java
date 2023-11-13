package com.moduinterview.user.entity;


import com.moduinterview.user.enums.Gender;
import com.moduinterview.user.enums.OauthType;
import com.moduinterview.user.enums.UserRole;
import com.moduinterview.user.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 100, unique = true)
  @Email(message = "이메일 형식에 맞게 작성해주세요.", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
  private String email; //oauth

  private String name;

  @Enumerated(javax.persistence.EnumType.STRING)
  private OauthType oAuthType; //google, naver, kakao, native
  private String oauthUserName;
  private String oauthId;

  private String password;

  @Enumerated(javax.persistence.EnumType.STRING)
  private UserStatus status;

  @NotNull
  @Enumerated(javax.persistence.EnumType.STRING)
  private Gender gender;

  @NotNull
  @Length(min = 11, max = 11, message = "전화번호는 11자리로 작성해주세요.")
  private String phone;

  private String profileImageUrl;

  @NotNull
  @Enumerated(javax.persistence.EnumType.STRING)
  private UserRole role;

  private String emailAuthKey;
  private Boolean emailAuthYn;
  private LocalDateTime emailAuthDate;

  private LocalDateTime regDate;
  private LocalDateTime modifiedDate;

//  private String roles; //User, Admin

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> roles = new ArrayList<>();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.getStringRoles());
    roles.add(authority);

    return roles;
  }


  @Override
  public String getUsername() {
    return String.valueOf(this.getId());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // 계정 만료 여부
  }

  @Override
  public boolean isAccountNonLocked() {
    return !(this.getStatus() == UserStatus.BLOCKED); // 계정 잠금 여부
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // 계정 패스워드 만료 여부
  }

  @Override
  public boolean isEnabled() {
    return isAccountNonExpired() && isAccountNonLocked() && isCredentialsNonExpired();
  }
}
