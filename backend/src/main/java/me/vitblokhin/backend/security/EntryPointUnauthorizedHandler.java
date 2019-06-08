package me.vitblokhin.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.vitblokhin.backend.dto.ExceptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("entryPointUnauthorizedHandler")
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Autowired
    public EntryPointUnauthorizedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        //httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        mapper.writeValue(httpServletResponse.getOutputStream(), new ExceptionDto("Access Denied"));
    }
} // class EntryPointUnauthorizedHandler
