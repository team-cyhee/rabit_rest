package com.cyhee.rabit.config.cmm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Oauth2 기반 인증 방식을 허용하는 Configuration Class
 * @author chy
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	/**
     * Jwt token을 해석하는데 사용되는 키
	 */
	private String publicKey;
	
	@Autowired
    public ResourceServerConfiguration(@Value("${config.oauth2.publicKey}") String publicKey) {
        this.publicKey = publicKey;
    }

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
				//.antMatchers("/rest/v1/users").access("#oauth2.hasScope('read')")
				.anyRequest().permitAll();
	}
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	
	@Autowired
	AuthenticationManager authenticationManager;
	
/*	@Bean
	public FilterRegistrationBean<OAuth2AuthenticationProcessingFilter> registration(OAuth2AuthenticationProcessingFilter filter) {
	    FilterRegistrationBean<OAuth2AuthenticationProcessingFilter> registration = new FilterRegistrationBean<>(filter);
	    //registration.setEnabled(false);
	    return registration;
	}
 */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(publicKey);
        return converter;
    }
     
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
    
}
