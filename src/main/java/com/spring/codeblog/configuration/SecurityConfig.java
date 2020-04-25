package com.spring.codeblog.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // URIs que não precisam passar por autenticação, ou seja, são as páginas públicas
    private static final String[] AUTH_LIST = {
            "/",
            "/posts",
            "/posts/{id}"
    };

    // Define gestão dos formulários que devem ser autenticados ou não.
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                // Lista com as URIs que não precisam de autenticação.
                .antMatchers(AUTH_LIST).permitAll()
                // Qualquer outro request fora da lista acima, necessitam de autenticação.
                .anyRequest().authenticated()
                // Permite que acesse a página de login sem solicitar autenticação (Óbvio)
                .and().formLogin().permitAll()
                // Permite que acesse a página de logout sem solicitar autenticação (Óbvio)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                // Caso desejemos uma página de login personalizada, essa página deve ser autorizada neste ponto acima.
    }

    // Define o usuário a ser autenticado em memória.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("carlos").password("{noop}123").roles("ADMIN");
        // O complemento do parâmetro password "{noop}" foi incluído devido a um bug no Spring Boot.
    }

    /*  Permite o acesso as pastas estáticas, por exemplo javascripts, bootstrap, css, etc.
        Em nosso caso não vamos precisar, pois utilizamos os componentes bootstrap por chamada na url.
     */
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/bootstrap/**");
//        web.ignoring().antMatchers("/bootstrap/**", "/style/**");
    }
}
