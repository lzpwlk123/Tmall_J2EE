package tmall.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.DAO.*;
import tmall.DAO.OrderItemDAO;
import tmall.DAO.ProductDAO;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
后台所有Servlet的父类，每一个板块的Servlet都继承该类。
由于所有Servlet在执行doGet()或者doPost()之前，都会先执行service(),因此设计这样一个父类，在父类中重写service方法，来完成一些共同操作，这样子类就可以
只写与板块相关的功能实现的代码了。
这些共同操作包括：
    1.分页
    2.反射
        通过反射，实现功能。
    3.每个实现具体功能的方法都返回一个String作为结果，根据这个String,决定如何回复前端
        String以 @ 开头: 比如删除操作，后台处理完成后，返回
                 %；
                 其他：
 */
public abstract class BaseBackServlet extends HttpServlet {
    //实现具体功能代码，每个子类必须实现自己板块的相应功能
    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;

    //这些是子类实现功能时需要用到的工具DAO。
    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int start = 0;
        int count = 5;

        //       当第一次访问admin_category_list时，那么没有start和count参数，就会丢出异常，所以这里要把异常捕获
        try{
            start = Integer.parseInt(req.getParameter("page.start"));
            count = Integer.parseInt(req.getParameter("page.count"));
        }catch (Exception e){

        }
        Page page = new Page(start,count);

        //2.反射部分的代码，用来实现功能
        String methodName = (String) req.getAttribute("method");
        System.out.println(this.getClass());  //子类继承父类后，子类调用父类的service方法，this.getClass()返回——子类的Class对象
        String redirect = null;
        try {
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class, Page.class);
            redirect = (String) method.invoke(this,req,resp,page);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (redirect.startsWith("@")){
            resp.sendRedirect(redirect.substring(1));
        }
        else if (redirect.startsWith("%")){

        }
        else {
            req.getRequestDispatcher(redirect).forward(req,resp);
        }
    }

    //用于接受上传文件。上传文件时，浏览器提交的数据是二进制，Servlet不能够通过request.getParameter("")直接获取参数。
    // 所以这里传入一个HashMap<String,String>,在处理上传文件时，顺便把非File字段的参数放入HashMap返回。
    public InputStream parseUpload(HttpServletRequest request, HashMap<String,String> parm) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //上传文件大小限制为10M
        factory.setSizeThreshold(1024 * 1024 * 10);
        List list = null;

        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator iterator = list.iterator();
        InputStream is = null;
        while (iterator.hasNext()) {
            FileItem item = (FileItem) iterator.next();
            if (item.isFormField()) {   // FormFiled，非文件字段，取出信息放入参数 HashMap<String,String> parm 中 返回
                String key = item.getFieldName();
                try {
                    String value = item.getString();
                    value = new String(value.getBytes("ISO-8859-1"),"utf-8");
//                    System.out.println("key,value：" + key + "  " + value);
                    parm.put(key,value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {  //文件字段，得到InputStream，返回
                try {
                    is = item.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return is;
    }

}
