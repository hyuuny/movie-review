package com.setge.talkingtoday.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private Long mid;
    private String email;
    private String password;
    private String nickname;
    private boolean fromSocial;
    private Map<String, Object> attr;

    public AuthMemberDTO(Long mid, String username, String password, String nickname,
                         boolean fromSocial, Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attr) {
        this(mid, username, password, nickname, fromSocial, authorities);
        this.attr = attr;
    }

    public AuthMemberDTO(Long mid, String username, String password, String nickname,
                         boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.mid = mid;
        this.email = username;
        this.password = password;
        this.nickname = nickname;
        this.fromSocial = fromSocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return this.nickname;
    }
}
