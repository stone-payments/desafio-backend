package br.com.stone.loja.conf;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableCaching
@ComponentScan(basePackages = "br.com.stone.loja")
public class AppWebConfiguration extends WebMvcConfigurerAdapter
{

   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
   {
      configurer.enable();
   }
   
   @Bean
   public InternalResourceViewResolver internalResourceViewResolver()
   {
      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
      resolver.setPrefix("/WEB-INF/views/");
      resolver.setSuffix(".jsp");
      return resolver;
   }

   @Bean
   public CacheManager cacheManager() {
       final SimpleCacheManager cacheManager = new SimpleCacheManager();
       cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("products"),
    		   new ConcurrentMapCache("transactions")));
       return cacheManager;
   }
}
