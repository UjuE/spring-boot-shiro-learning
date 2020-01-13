package com.ujuezeoke.learning.springbootshiro.configuration.shiro;

import org.apache.shiro.authc.AuthenticationListener;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.PropertiesRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;

@Configuration
public class ShiroConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Bean("passwordAuth")
    public FormAuthenticationFilter formAuthenticationFilter() {
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter() {
            @Override
            protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
                LOGGER.info("Password Auth Filter login success");
                return super.onLoginSuccess(token, subject, request, response);
            }

            @Override
            public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
                LOGGER.info("This is where things happen");
                return super.onPreHandle(request, response, mappedValue);
            }
        };
        formAuthenticationFilter.setLoginUrl("/login");

        return formAuthenticationFilter;
    }

    @Bean
    public Realm propertiesRealm() {
        return new PropertiesRealm();
    }

    @Bean
    public Collection<Realm> allRealms() {
        ListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();
        return listableBeanFactory.getBeansOfType(Realm.class).values();
    }

    @Bean
    public Collection<AuthenticationListener> authenticationListeners() {
        return new DefaultListableBeanFactory()
                .getBeansOfType(AuthenticationListener.class)
                .values();
    }

    @Bean
    public Authenticator authenticator(Collection<AuthenticationListener> authenticationListeners) {
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationListeners(authenticationListeners);
        return modularRealmAuthenticator;
    }

    @Bean
    public Authorizer authorizer() {
        return new ModularRealmAuthorizer();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        SimpleCookie sessionIdCookie = new SimpleCookie();
        sessionIdCookie.setName("APPSESSION");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setPath("/");
        defaultWebSessionManager.setSessionIdCookie(sessionIdCookie);
        return defaultWebSessionManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

        shiroFilterChainDefinition.addPathDefinition("/login", "passwordAuth[permissive]");
        shiroFilterChainDefinition.addPathDefinition("/**", "passwordAuth");
        return shiroFilterChainDefinition;
    }

    @Bean("appSecurityManager")
    public SecurityManager securityManager(SessionManager sessionManager, Collection<Realm> allRealms) {
        LOGGER.info("Creating Security Manager");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSubjectDAO(new DefaultSubjectDAO());
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setRealms(allRealms);

        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        LOGGER.info("Creating ShiroFilterFactoryBean");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/hello");
        return shiroFilterFactoryBean;
    }

}
