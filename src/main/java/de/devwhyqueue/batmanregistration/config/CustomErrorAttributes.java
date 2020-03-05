package de.devwhyqueue.batmanregistration.config;

import de.devwhyqueue.batmanregistration.resource.exception.ResponseStatusExceptionWithCode;
import java.util.Map;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

@Configuration
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

    Throwable t = super.getError(webRequest);
    if (t instanceof ResponseStatusExceptionWithCode) {
      errorAttributes.put("code", ((ResponseStatusExceptionWithCode) t).getCode());
    }

    return errorAttributes;
  }
}
