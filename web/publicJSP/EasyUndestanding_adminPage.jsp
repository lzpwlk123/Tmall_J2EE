<%--简化版 分页功能，没有加入各种边界处理--%>
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
        <li>
            <a href="" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span> <%--  	&laquo; 是html特殊字符  表示： «  --%>
            </a>
        </li>
       <li><a href="">1</a></li>
       <li><a href="">2</a></li>
       <li><a href="">3</a></li>
       <li><a href="">4</a></li>
       <li class="disabled"><a href="/index.jsp">5</a></li> <%-- class这是为disable后，鼠标悬浮于上，就变成禁止。但实际上仍然可以点击，并且会跳转
                                                                  所以别忘记设置click函数来阻止事件发生--%>
       <li>
           <a href="" aria-label="Next">
               <span aria-hidden="true">&raquo;</span> <%--  	&raquo; 是html特殊字符  表示： »  --%>
           </a>
       </li>
   </ul>
</body>
