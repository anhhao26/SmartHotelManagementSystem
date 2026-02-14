package controller.reception;

import dao.BillingDAO;
import model.CartItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            // Xử lý Check-in
            if ("checkin".equalsIgnoreCase(action)) {
                int bid = Integer.parseInt(req.getParameter("bid"));
                int rid = Integer.parseInt(req.getParameter("rid"));

                BillingDAO dao = new BillingDAO();
                if (dao.processCheckIn(bid, rid)) {
                    resp.sendRedirect("reception/home.jsp?msg=Check-in Success");
                } else {
                    resp.getWriter().println("Check-in Failed!");
                }
                return;
            }

            // Xử lý Check-out & Thanh toán
            if ("checkout".equalsIgnoreCase(action)) {
                int bid = Integer.parseInt(req.getParameter("bid"));
                int cid = Integer.parseInt(req.getParameter("cid"));
                int rid = Integer.parseInt(req.getParameter("rid"));
                double price = Double.parseDouble(req.getParameter("price"));

                // parse selected items from form: name="itemId" (checkboxes) + qty_{id}
                List<CartItem> items = new ArrayList<>();
                String[] itemIds = req.getParameterValues("itemId");
                if (itemIds != null) {
                    for (String sId : itemIds) {
                        try {
                            int itemId = Integer.parseInt(sId);
                            String qtyParam = req.getParameter("qty_" + itemId);
                            int q = 1;
                            if (qtyParam != null && !qtyParam.isEmpty()) {
                                try { q = Integer.parseInt(qtyParam); } catch (NumberFormatException ex) { q = 1; }
                            }
                            if (q > 0) items.add(new CartItem(itemId, q));
                        } catch (NumberFormatException ex) {
                            // ignore invalid id
                        }
                    }
                }

                BillingDAO dao = new BillingDAO();
                if (dao.processCheckOut(bid, cid, rid, items, price)) {
                    resp.sendRedirect("reception/home.jsp?msg=Payment Success - Room is Cleaning");
                } else {
                    resp.getWriter().println("Payment Failed (Rollback)!");
                }
                return;
            }

            // nếu không action nào khớp
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}