package com.projectivesoftware.distillehr.configuration;

import com.projectivesoftware.distillehr.domain.empi.Person;
import com.projectivesoftware.distillehr.service.EmpiService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class EmpiServiceTestConfiguration {

    @Bean
    @Primary
    public EmpiService empiServiceTest() {
        EmpiService empiService = Mockito.mock(EmpiService.class);

        try {
            Mockito.when(empiService.findByPersonId("bcfbdbd0-6b69-11e7-b933-005056a06f55")).thenReturn(new Person());
        } catch (Exception e) {

        }

        return empiService;
    }
}
