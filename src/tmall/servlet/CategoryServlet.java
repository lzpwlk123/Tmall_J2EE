package tmall.servlet;

import tmall.DAO.CategoryDAO;
import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/*
Category板块的Servlet类，一个类实现所有功能。
继承了父类的service方法，在这个方法里面由request携带的method属性，决定哪个功能方法，并通过反射调用
父类中还定义了所有板块需要的DAO工具类，在子类中直接调用
每个方法 返回不同格式的字符串，用以在service方法中决定怎么给前端回复

add 方法涉及到 图片的上传
 */
public class CategoryServlet extends BaseBackServlet {

    //增
    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        HashMap<String,String> parm = new HashMap<>();
        InputStream is = super.parseUpload(request,parm);
        Category bean = new Category();
        bean.setName(parm.get("name"));
//        System.out.println("parm.get(\"name\") ：" + parm.get("name"));
        categoryDAO.add(bean);

//这里的路径有说法，部署路径和 开发路径。这里写的代码，图片会上传到部署路径下，开发路径中没有  
        File imgFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        if (!imgFolder.exists()) imgFolder.mkdirs();
        File file = new File(imgFolder, bean.getId() + ".jpg");

        try {
            if (null != is && 0 != is.available()){
                try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(bytes))){
//                        System.out.println("length = " + length);
                        fileOutputStream.write(bytes,0, length);
                    }
                    fileOutputStream.flush();
                    //通过如下代码，把文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }
    //删
    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    //edit 和 update共同完成 改
    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Category bean  = categoryDAO.get(id);
        request.setAttribute("category",bean);
        return "admin/editCategory.jsp";
    }

    //add和update相比区别不大，两点见下
    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        HashMap<String,String> parm = new HashMap<>();
        InputStream is = super.parseUpload(request,parm);
        Category bean = new Category();
        bean.setName(parm.get("name"));
        bean.setId(Integer.parseInt(parm.get("id")));   // 区别1
//        System.out.println("parm.get(\"name\") ：" + parm.get("name"));
        categoryDAO.update(bean);

//这里的路径有说法，部署路径和 开发路径。这里写的代码，图片会上传到部署路径下，开发路径中没有
        File imgFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        if (!imgFolder.exists()) imgFolder.mkdirs();
        File file = new File(imgFolder, bean.getId() + ".jpg");

        try {
//            如果通过parseUpload 获取到的输入流是空的，或者其中的可取字节数为0，那么就不进行上传处理
            if (null != is && 0 != is.available()){   // 区别2 update时允许不上传图片
                try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
                    byte[] bytes = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(bytes))){
//                        System.out.println("length = " + length);
                        fileOutputStream.write(bytes,0, length);
                    }
                    fileOutputStream.flush();
                    //通过如下代码，把文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }
    //查
    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> categoryList = categoryDAO.list(page.getStart(),page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("page",page);
        request.setAttribute("categoryList",categoryList);

        return "admin/listCategory.jsp";
    }
}
