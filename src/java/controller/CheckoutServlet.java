package com.smarthotel.controller;

import com.smarthotel.model.CartItem;
import com.smarthotel.service.BillingService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {
    private final BillingService billingService = new BillingService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            if ("checkin".equalsIgnoreCase(action)) {
                int bid = Integer.parseInt(req.getParameter("bid"));
                boolean ok = billingService.processCheckIn(bid);
                if (ok) resp.sendRedirect(req.getContextPath() + "/reception/home.jsp?msg=Check-in Success");
                else resp.getWriter().println("Check-in Failed!");
                return;
            }

            if ("checkout".equalsIgnoreCase(action)) {
                int bid = Integer.parseInt(req.getParameter("bid"));
                int cid = Integer.parseInt(req.getParameter("cid"));
                int rid = Integer.parseInt(req.getParameter("rid"));
                double price = Double.parseDouble(req.getParameter("price"));

                List<CartItem> items = new ArrayList<>();
                String[] itemIds = req.getParameterValues("itemId");
                if (itemIds != null) {
                    for (String sId : itemIds) {
                        int itemId = Integer.parseInt(sId);
                        String qtyParam = req.getParameter("qty_" + itemId);
                        int q = 1;
                        if (qtyParam != null && !qtyParam.isEmpty()) {
                            try { q = Integer.parseInt(qtyParam); } catch (NumberFormatException ex) {}
                        }
                        if (q > 0) items.add(new CartItem(itemId, q));
                    }
                }

                boolean ok = billingService.processCheckOut(bid, cid, rid, items, price);
                if (ok) resp.sendRedirect(req.getContextPath() + "/reception/home.jsp?msg=Payment Success - Room is Cleaning");
                else resp.getWriter().println("Payment Failed (Rollback)!");
                return;
            }

            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.getWriter().println("Error: " + ex.getMessage());
        }
    }
}