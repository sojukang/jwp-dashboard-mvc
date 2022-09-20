package nextstep.mvc.controller.tobe;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nextstep.mvc.HandlerAdaptor;
import nextstep.mvc.controller.asis.ForwardController;
import nextstep.mvc.view.ModelAndView;
import samples.TestController;

class AnnotationHandlerAdaptorTest {

    @Test
    @DisplayName("Adaptor가 지원하는 Handler인지 검증한다(true).")
    void supports_true() {
        // given
        final HandlerAdaptor adaptor = new AnnotationHandlerAdaptor();
        final Object controller = new TestController();
        final Method method = controller.getClass().getDeclaredMethods()[0];
        final HandlerExecution execution = new HandlerExecution(controller, method);

        // when, then
        assertThat(adaptor.supports(execution)).isTrue();
    }

    @Test
    @DisplayName("Adaptor가 지원하는 Handler인지 검증한다(false).")
    void supports_false() {
        // given
        final HandlerAdaptor adaptor = new AnnotationHandlerAdaptor();
        final Object controller = new ForwardController("path");

        // when, then
        assertThat(adaptor.supports(controller)).isFalse();
    }

    @Test
    @DisplayName("Adaptor의 handle을 검증한다.")
    void handle() {
        // given
        final HandlerAdaptor adaptor = new AnnotationHandlerAdaptor();
        final Object controller = new TestController();
        final Method firstMethod = controller.getClass().getDeclaredMethods()[0];
        final HandlerExecution handler = new HandlerExecution(controller, firstMethod);

        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        // when
        final ModelAndView modelAndView = adaptor.handle(request, response, handler);
        final String id = (String)modelAndView.getAttribute("id");

        // then
        assertThat(id).isEqualTo("gugu");
    }
}
