package com.egs.bankservice.model.service.security;


import com.egs.bankservice.model.dto.response.TokenRes;
import com.egs.bankservice.model.entity.EgsUser;
import com.egs.bankservice.model.entity.security.CustomUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Oauth2Service {
    private final DefaultTokenServices defaultTokenServices;

    @Autowired
    public Oauth2Service(DefaultTokenServices defaultTokenServices) {
        this.defaultTokenServices = defaultTokenServices;
    }

    public TokenRes getToken(EgsUser user, String username, String plainPassword) {
        HashMap<String, String> authorizationParameters = getAuthorizationParameters(username, plainPassword);
        Set<String> responseType = getResponseType();
        Set<String> scopes = getScopes();

        OAuth2Request authorizationRequest = new OAuth2Request(
                authorizationParameters, "client_id",
                null, true, scopes, null, "",
                responseType, null);

        CustomUserPrincipal userPrincipal = new CustomUserPrincipal(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, null);

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest, authenticationToken);
        authenticationRequest.setAuthenticated(true);

        OAuth2AccessToken accessToken = defaultTokenServices.createAccessToken(authenticationRequest);
        return new TokenRes(accessToken.getValue(), accessToken.getRefreshToken().getValue());
    }

    private HashMap<String, String> getAuthorizationParameters(String username, String plainPassword) {
        return new HashMap<String, String>() {{
            put("scope", "read");
            put("username", username);
            put("password", plainPassword);
            put("client_id", "client_id");
            put("grant", "password");
        }};
    }

    private Set<String> getResponseType() {
        return new HashSet<String>() {{
            add("password");
        }};
    }

    private Set<String> getScopes() {
        return new HashSet<String>() {{
            add("read");
            add("write");
        }};
    }
}
