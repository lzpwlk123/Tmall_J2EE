<%@page pageEncoding="UTF-8" %>
<script>
<%--    阻止事件发生--%>
    $(function () {
        $("ul.pagination li.disabled a").click(function () {
            return false;
        })
    })
</script>
<body>
   <ul class="pagination"> <%-- pagination:n. 标记页数；页码--%>

<%--       回到首页--%>
<%-- <li <c:if test="${0 == page.start}"> class="disabled" </c:if> >
上下两种写法等价，Page符合JavaBean,hasPreviouse会的导致isHasPreviouse()方法被调用--%>
       <li <c:if test="${!page.hasPrevious}"> class="disabled" </c:if> >  <%--如果还有前一页，那么回到首页可点击--%>
       <a href="?page.start=0" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span> <%--  	&laquo; 是html特殊字符  表示： «  --%>
            </a>
       </li>

<%--       上一页--%>
       <li <c:if test="${!page.hasPrevious}"> class="disabled" </c:if> >  <%--如果还有前一页，那么上一页可点击--%>
           <a href="?page.start=${page.start - page.count}" aria-label="Previous">
               <span>&lsaquo;</span> <%--  	&laquo; 是html特殊字符  表示： ‹  --%>
           </a>
       </li>

<%--       具体的页码--%>
        <c:forEach begin="0" end="${page.totalPage - 1}" varStatus="vs">
<%--            如果总共有100页，不可能1~100全显示出来，所以这里需要限制一下显示的数目
                如果每页显示5个，那么按照下面的代码，会显示前2页和后4页
                c:forEach varStatus的 count属性 当前这次迭代从 1 开始的迭代计数--%>
            <c:if test="${vs.count * page.count - page.start <= 20 && vs.count * page.count - page.start >= -10}">
<%--                如果是当前页面的页码，那么不能点击，设置为disabled。
                    c:forEach varStatus的index属性：当前这次迭代从 0 开始的迭代索引--%>
                <li <c:if test="${vs.index * page.count == page.start}">class="disabled"</c:if> >
                        <%-- 如果是当前页面的页码，设置a的class为current,会有数字加粗的效果--%>
                    <a href="?page.start=${vs.index * page.count}"
                       <c:if test="${vs.index * page.count == page.start}">class="current"</c:if> >
                 ${vs.count}
             </a>
         </li>
     </c:if>
 </c:forEach>

<%--       下一页--%>
       <li <c:if test="${!page.hasNext}">class="disabled" </c:if> >
           <a href="?page.start=${page.start + page.count}" aria-label="Next">
               <span>&rsaquo;</span>
           </a>
       </li>

<%--       最后一页--%>
       <li <c:if test="${!page.hasNext}">class="disabled" </c:if> >
           <a href="?page.start=${page.last}" aria-label="Next">
               <span aria-hidden="true">&raquo;</span> <%--  	&raquo; 是html特殊字符  表示： »  --%>
           </a>
       </li>
   </ul>
</body>
