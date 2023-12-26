package org.quarkus.demo.todolist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonp.JSONPModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class RegisterCustomModuleCustomizer implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        mapper.registerModule(new JSONPModule());
    }
}
