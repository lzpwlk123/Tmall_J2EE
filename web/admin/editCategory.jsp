<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../publicJSP/adminHeader.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<script>
<%--    编辑时，图片允许置空--%>
    $(function () {
        $("form#updateForm").submit(function () {
            if (!checkEmpty("name","分类名称"))
                return false;
            return true;
        })
    })
</script>
<body>
<%@include file="../publicJSP/adminNavigator.jsp"%>
<%--编辑页面照搬list页面的add部分--%>
    <div class="pageDiv">
        <div class="panel panel-warning editDiv">
            <div class="panel-heading"> 待编辑的分类 </div> <%-- 面板标题 --%>
            <div class="panel-body">
                <form method="post" action="admin_category_update" id="updateForm" enctype="multipart/form-data">
                    <table class="updateTable">
<%--                        value 属性值，在页面上显示未修改之前的值--%>
                        <tr><td>分类名称  </td><td><input value="${category.name}" type="text" name="name" class="form-control" id="name"><br></td></tr>
    <%--                      <input type="file" ...> 这种 input 不能用 value --%>
                        <tr><td>分类图片  </td><td><input type="file" name="picture" accept="image/*" id="picture"></td></tr>
                        <tr class="submitTR">
                            <td colspan="2" align="center"> <%--colspan 表示该列跨两个单元格--%>
<%--                                把id隐藏，不显示在页面上，但也会提交到后台--%>
                                <input value="${category.id}" type="hidden" name="id" class="form-control" id="id">
                                <button type="submit" class="btn btn-success">提 交</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
