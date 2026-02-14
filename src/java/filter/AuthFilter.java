package filter;
import model.Account;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AuthFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("acc");
        
        String url = request.getRequestURI();
        
        // Nếu chưa login mà đòi vào trang admin hoặc reception -> Đá về login
        if (acc == null && (url.contains("/admin") || url.contains("/reception"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // Nếu là Khách mà đòi vào admin -> Chặn
        if (acc != null && acc.getRole().equals("GUEST") && (url.contains("/admin") || url.contains("/reception"))) {
            response.sendRedirect(request.getContextPath() + "/guest/profile.jsp");
            return;
        }
        
        chain.doFilter(req, res);
    }
}