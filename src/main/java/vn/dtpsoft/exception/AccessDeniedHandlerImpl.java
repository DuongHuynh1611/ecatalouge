package vn.dtpsoft.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper mapper;
    public AccessDeniedHandlerImpl() {
        this.mapper = new ObjectMapper();
    }

    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        //Some CSRF related code

        ApiError apiError = new ApiError(FORBIDDEN);
        apiError.setMessage(e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(FORBIDDEN.value());
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }
}