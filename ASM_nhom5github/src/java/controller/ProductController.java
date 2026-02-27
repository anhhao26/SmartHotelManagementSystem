package controller;

import dao.ProductDAO;
import dao.SupplierDAO;
import entity.Product;
import entity.Supplier;
import service.ProductService;      
import service.ProductServiceImpl;
import service.SupplierService;
import service.SupplierServiceImpl;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductController", urlPatterns = {"/products"})
public class ProductController extends HttpServlet {

    private ProductService productService; 
    private SupplierService supplierService; 

    @Override
    public void init() {
        productService = new ProductServiceImpl();
        supplierService = new SupplierServiceImpl();
    }

    // GET ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                // 1. Quản lý Sản phẩm
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "import": // Form Nhập hàng riêng biệt
                    showImportForm(request, response);
                    break;


                // 2. Quản lý Nhà cung cấp
                case "listSuppliers":
                    listSuppliers(request, response);
                    break;
                case "newSupplier":
                    showSupplierForm(request, response);
                    break;
                case "editSupplier":
                    showEditSupplierForm(request, response);
                    break;
                case "deleteSupplier":
                    deleteSupplier(request, response);
                    break;

                // Mặc định: Hiện danh sách sản phẩm
                default:
                    listProducts(request, response);
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                // --- Xử lý Sản phẩm ---
                case "insert":
                    insertProduct(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
                case "saveImport":
                    saveImport(request, response);
                    break;
                    
                // --- Xử lý Xóa / Trạng thái ---
                case "softDelete":
                    softDeleteProduct(request, response);
                    break;
                case "restore":
                    restoreProduct(request, response);
                    break;
                case "hardDelete":
                    hardDeleteProduct(request, response);
                    break;

                // --- Xử lý Nhà cung cấp ---
                case "saveSupplier":
                    saveSupplier(request, response);
                    break;

                default:
                    response.sendRedirect("products");
                    break;
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    // --- CÁC HÀM HIỂN THỊ VIEW (JSP) ---

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    // 1. Lấy tham số từ URL (VD: products?showHidden=true)
    String showHidden = request.getParameter("showHidden");
    
    List<Product> listProducts;

    // 2. Logic lọc dữ liệu
    if ("true".equals(showHidden)) {
        // Nếu chọn "Hiện hàng ẩn" -> Gọi hàm lấy tất cả
        listProducts = productService.findAll();
    } else {
        // Mặc định -> Chỉ gọi hàm lấy hàng đang Active
        listProducts = productService.findActiveOnly();
    }

    // 3. Gửi dữ liệu và trạng thái của nút checkbox về JSP
    request.setAttribute("listProducts", listProducts);
    request.setAttribute("isShowHidden", "true".equals(showHidden)); // Để JSP biết mà tick vào checkbox
    
    request.getRequestDispatcher("Product/product-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Supplier> listSuppliers = supplierService.findAll();
        request.setAttribute("listSuppliers", listSuppliers);
        request.getRequestDispatcher("Product/product-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productService.findById(id);
        List<Supplier> listSuppliers = supplierService.findAll();
        
        request.setAttribute("product", existingProduct);
        request.setAttribute("listSuppliers", listSuppliers);
        request.getRequestDispatcher("Product/product-form.jsp").forward(request, response);
    }
    
    //  Form nhập hàng
    private void showImportForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productService.findById(id);
        request.setAttribute("product", p);
        request.getRequestDispatcher("Product/import-form.jsp").forward(request, response);
    }

    // --- CÁC HÀM XỬ LÝ SUPPLIER ---
    
    private void listSuppliers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Supplier> list = supplierService.findAll();
        request.setAttribute("listSuppliers", list);
        request.getRequestDispatcher("Product/supplier-list.jsp").forward(request, response);
    }

    private void showSupplierForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Product/supplier-form.jsp").forward(request, response);
    }

    private void showEditSupplierForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Supplier s = supplierService.findById(id);
        request.setAttribute("supplier", s);
        request.getRequestDispatcher("Product/supplier-form.jsp").forward(request, response);
    }

    private void saveSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        Supplier s = new Supplier();
        if (idStr != null && !idStr.isEmpty()) {
            s.setSupplierID(Integer.parseInt(idStr));
        }
        s.setSupplierName(name);
        s.setContactPhone(phone);
        s.setAddress(address);

        supplierService.save(s);
        response.sendRedirect("products?action=listSuppliers");
    }

    private void deleteSupplier(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            supplierService.delete(id);
            response.sendRedirect("products?action=listSuppliers");
        } catch (Exception e) {
            response.sendRedirect("products?action=listSuppliers&error=CannotDelete");
        }
    }
    // ==========================================
    // CÁC HÀM XỬ LÝ POST 
  

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product newProduct = new Product();
        newProduct.setProductName(request.getParameter("name"));
        newProduct.setUnit(request.getParameter("unit"));
        newProduct.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        newProduct.setCostPrice(Double.parseDouble(request.getParameter("costPrice")));
        
        boolean isTradeNew = "1".equals(request.getParameter("isTradeGood"));
        newProduct.setIsTradeGood(isTradeNew);
        newProduct.setSellingPrice(isTradeNew ? Double.parseDouble(request.getParameter("sellingPrice")) : 0);
        
        Supplier sNew = new Supplier();
        sNew.setSupplierID(Integer.parseInt(request.getParameter("supplierID")));
        newProduct.setSupplier(sNew);
        
        productService.createNewProduct(newProduct);
        response.sendRedirect("products");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product updateProduct = new Product();
        updateProduct.setProductID(Integer.parseInt(request.getParameter("id")));
        updateProduct.setProductName(request.getParameter("name"));
        updateProduct.setUnit(request.getParameter("unit"));
        
        boolean isTradeUpdate = "1".equals(request.getParameter("isTradeGood"));
        updateProduct.setIsTradeGood(isTradeUpdate);
        updateProduct.setSellingPrice(isTradeUpdate ? Double.parseDouble(request.getParameter("sellingPrice")) : 0);
        
        Supplier sUpdate = new Supplier();
        sUpdate.setSupplierID(Integer.parseInt(request.getParameter("supplierID")));
        updateProduct.setSupplier(sUpdate);
        
        productService.updateProductInfo(updateProduct);
        response.sendRedirect("products");
    }

    private void saveImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idImport = Integer.parseInt(request.getParameter("id"));
        int qtyToAdd = Integer.parseInt(request.getParameter("quantityToAdd"));
        double newCost = Double.parseDouble(request.getParameter("newCostPrice"));
        
        productService.importStock(idImport, qtyToAdd, newCost);
        response.sendRedirect("products");
    }

    private void softDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.softDelete(id);
        response.sendRedirect("products");
    }

    private void restoreProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.restore(id);
        response.sendRedirect("products?showHidden=true");
    }

    private void hardDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.hardDelete(id);
        response.sendRedirect("products");
    }
}