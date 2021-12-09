package epam.jwd.online_training.filter;

import epam.jwd.online_training.command.bundle.PagePathManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"})
public class PageRedirectSecurityFilter implements Filter {

    private static final String PATH_PAGE_INDEX = "path.page.index";
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = PagePathManager.getProperty(PATH_PAGE_INDEX);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletContext servletContext = httpServletRequest.getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(indexPath);
        requestDispatcher.forward(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
    }
}
