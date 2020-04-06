<%@include file="../publicJSP/adminNavigator.jsp"%>
<%@include file="../publicJSP/adminHeader.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <title>分类管理</title>
</head>
<script>
    //add功能时，对分类名称和分类图片做了为空判断，当为空的时候，不能提交
    $(function () {
        $("form#addForm").submit(function () {
            if (!checkEmpty("name","分类名称"))
                return false;
            if (!checkEmpty("picture","分类图片"))
                return  false;
            return true;
        })
    })
</script>
<body>
   <div class="workingArea"> <%-- css\back\style.css的样式--%>
       <h1 class="label label-info">分类管理</h1>
       <br>
       <br>
<%--       bootstrap中的表格，带边框，有鼠标悬停状态，带斑马线，更加紧凑--%>
       <table class="table table-bordered table-hover table-striped table-condensed">
           <thead > <%--<thead> 标签用于组合 HTML 表格的表头内容。
                        与 <tbody> 和 <tfoot> 元素结合起来使用，用来规定表格的各个部分（表头、主体、页脚）--%>
                <tr class="success">   <%--bootstrap中的样式,让表头这一行背景变绿--%>
<%--                    <th> 标签定义 HTML 表格中的表头单元格。--%>
<%--                        HTML 表格有两种单元格类型：--%>
<%--                            表头单元格 - 包含头部信息（由 <th> 元素创建）元素中的文本通常呈现为粗体--%>
<%--                            标准单元格 - 包含数据（由 <td> 元素创建）元素中的文本通常是普通的左对齐文本--%>
                    <th>ID</th>
                    <th>图片</th>
                    <th>分类名称</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
           </thead>
           <tbody>
<%--            <% System.out.println(request.getAttribute());%>--%>
                <c:forEach items="${categoryList}" var="category" varStatus="vs">
                    <tr>
                        <td>${category.id}</td>
                        <td><img height="40px" src="img/category/${category.id}.jpg"></td>
                        <td>${category.name}</td>
                        <td><a href="admin_category_edit?id=${category.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                        <td><a deleteLink="true" href="admin_category_delete?id=${category.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                    </tr>
                </c:forEach>
           </tbody>
       </table>
      <div class="pageDiv"> <%-- pageDiv使页码框居中 --%>
           <%@include file="../publicJSP/adminPage.jsp"%>
       </div>
       <div class="panel panel-warning addDiv"> <%--boostrap面板 面板标题黄色  自己定义的--%>
          <div class="panel-heading"> 新增分类 </div> <%-- 面板标题 --%>
          <div class="panel-body">
              <form method="post" action="admin_category_add" id="addForm" enctype="multipart/form-data">
                  <table class="addTable">
                      <tr><td>分类名称</td><td><input type="text" name="name" class="form-control" id="name"><br></td></tr>
<%--                      accept 属性规定了可通过文件上传提交的服务器接受的文件类型。仅适用于 <input type="file">
                                audio/*	接受所有的声音文件。
                                video/*	接受所有的视频文件。
                                image/*	接受所有的图像文件--%>
                      <tr><td>分类图片</td><td><input type="file" name="picture" accept="image/*" id="picture"></td></tr>
                      <tr class="submitTR">
                          <td colspan="2" align="center"> <%--colspan 表示该列跨两个单元格   --%>
                              <button type="submit" class="btn btn-success">提 交</button>
                          </td>
                      </tr>
                  </table>
              </form>
          </div>
       </div>
   </div>
   <%@include file="../publicJSP/adminFooter.jsp"%>
</body>
</html>
