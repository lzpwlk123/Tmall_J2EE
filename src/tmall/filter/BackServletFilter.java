package tmall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
/*
大项目中，传统的在web.xml中配置Servlet，一个路径对应一个Servlet的思路 造成的配置文件臃肿问题。每个板块的每个功能（CRUD）都要有一个单独的Servlet。
Filter + Servlet的设计模式：可以使每个板块只用一个Servlet,就能满足CRUD一系列业务要求
思路：
    1.请求的url(?还是uri) 设计为 admin_板块_功能
    2.BackServletFilter为所有请求的过滤器。放行不以“/admin_”开头的请求（这些都是请求图片，css等静态资源的）
      其他请求，都截取url字段，这样就知道了前台请求的 板块和功能。把功能设为Attribute，再用后台转发功能，把请求转发给对应板块的Servlet。
    3.对应板块的Servlet，不需要为每个增删改查每个功能单独写Servlet,并配置web.xml。现在只需要写一个板块Servlet,接收到过滤器转发的请求后，
      用反射来调用对应的功能方法。
 */
public class BackServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//        System.out.println("进入BackServletFilter");

        String contextPath = request.getServletContext().getContextPath();
        String requestURI = request.getRequestURI();
        System.out.println("contextPath：" + contextPath + "  requestURI：" + requestURI);
//        contextPath：/Tmall  requestURI：/Tmall/admin/listCategory.jsp
        requestURI = StringUtils.remove(requestURI, contextPath);

//        if (requestURI.contains("admin")){
        if (requestURI.startsWith("/admin_")){
            String desServlet = StringUtils.substringBetween(requestURI, "_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(requestURI, "_");
            request.setAttribute("method",method);
            request.getRequestDispatcher("/" + desServlet).forward(request,response);
            return;
        }else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
