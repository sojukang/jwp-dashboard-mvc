package com.techcourse;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techcourse.controller.typed.TypedLoginController;
import com.techcourse.controller.typed.TypedLoginViewController;
import com.techcourse.controller.typed.TypedLogoutController;
import com.techcourse.controller.typed.TypedRegisterController;
import com.techcourse.controller.typed.TypedRegisterViewController;

import jakarta.servlet.http.HttpServletRequest;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.controller.asis.Controller;
import nextstep.mvc.controller.asis.ForwardController;

public class ManualHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(ManualHandlerMapping.class);

    private static final Map<String, Controller> controllers = new HashMap<>();

    @Override
    public void initialize() {
        controllers.put("/typed", new ForwardController("/index.jsp"));
        controllers.put("/typed/login", new TypedLoginController());
        controllers.put("/typed/login/view", new TypedLoginViewController());
        controllers.put("/typed/logout", new TypedLogoutController());
        controllers.put("/typed/register/view", new TypedRegisterViewController());
        controllers.put("/typed/register", new TypedRegisterController());

        log.info("Initialized Handler Mapping!");
        controllers.keySet()
            .forEach(path -> log.info("Path : {}, Controller : {}", path, controllers.get(path).getClass()));
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        final String requestURI = request.getRequestURI();
        log.debug("Request Mapping Uri : {}", requestURI);
        return controllers.get(requestURI);
    }
}
