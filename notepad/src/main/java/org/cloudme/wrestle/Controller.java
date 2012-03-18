package org.cloudme.wrestle;

import static org.cloudme.wrestle.annotation.AnnotationUtils.hasAnnotation;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;

import org.cloudme.wrestle.annotation.Get;
import org.cloudme.wrestle.annotation.Post;
import org.cloudme.wrestle.annotation.UrlMapping;

public class Controller extends HttpServlet {
    private final Collection<RequestHandler> httpGetHandlers = new ArrayList<RequestHandler>();
    private final Collection<RequestHandler> httpPostHandlers = new ArrayList<RequestHandler>();

    protected void register(ActionHandler handler) {
        String urlMapping = handler.getClass().getAnnotation(UrlMapping.class).value();
        if (!urlMapping.endsWith("/")) {
            urlMapping += "/";
        }
        for (Method method : handler.getClass().getMethods()) {
            addIfHasAnnotation(Get.class, method, handler, urlMapping, httpGetHandlers);
            addIfHasAnnotation(Post.class, method, handler, urlMapping, httpPostHandlers);
        }
    }

    private void addIfHasAnnotation(Class<? extends Annotation> annotation,
            Method method,
            ActionHandler handler,
            String urlMapping,
            Collection<RequestHandler> requestHandlers) {
        if (hasAnnotation(method, annotation)) {
            requestHandlers.add(new RequestHandler(handler, method, urlMapping + "/" + method.getName()));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp, httpGetHandlers);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp, httpPostHandlers);
    }

    @SneakyThrows
    private void execute(HttpServletRequest req, HttpServletResponse resp, Collection<RequestHandler> requestHandlers) {
        String path = req.getContextPath();
        for (RequestHandler handler : requestHandlers) {
            if (handler.matches(path)) {
                Object value = handler.execute(path, req, resp);
            }
        }
    }
}