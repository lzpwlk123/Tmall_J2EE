<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%--contentType="text/html; charset=UTF-8" 告诉浏览器使用UTF-8进行中文编码识别--%>
<%--pageEncoding="UTF-8" 本jsp上的中文文字，使用UTF-8进行编码--%>
<%--isELIgnored="false" 本jsp上会使用EL表达式--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>
<%--使用c 和fmt 两种标准标签库，需要导入jstl.jar 和standard.jar--%>
<html>

<head>
    <script src="js/jquery/2.0.0/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="css/back/style.css" rel="stylesheet">

    <script>
        // 预先定义一些判断输入框的函数，方便后面使用
        // 注意：  不空 返回true
        function checkEmpty(id,name) {
            var element = $("#" + id); //相当于 document.getElementById("input1").value;
            var value = element.val();
            if (0 == value.length){
                alert(name + "不能为空");
                element[0].focus(); //聚焦到输入框
                return false;
            }
            return true;
        }

        function checkNumber(id,name) {
            var element = $("#" + id); //相当于 document.getElementById("input1").value;
            var value = element.val();
            if (0 == value.length){
                alert(name + "不能为空");
                element[0].focus(); //聚焦到输入框
                return false;
            }else if (isNaN(value)){
                alert(name + "必须是数字");
                element[0].focus(); //聚焦到输入框
                return false;
            }
            return true;
        }

        function checkInt(id, name){
            var value = $("#"+id).val();
            if(value.length==0){
                alert(name+ "不能为空");
                $("#"+id)[0].focus();
                return false;
            }
            if(parseInt(value)!=value){
                alert(name+ "必须是整数");
                $("#"+id)[0].focus();
                return false;
            }

            return true;
        }
        // 删除功能的超链，需要有确认功能
        // 删除功能的超链赋予deleteLink属性，并且设置为true。其余功能的超链不受此函数影响
        $(document).ready(function () {
            $("a").click(function () {
                var deleteLink = $(this).attr("deleteLink");
                console.log(deleteLink);
                if("true"==deleteLink){
                    var confirmDelete = confirm("确认要删除"); //confirm()弹出确认框，该方法返回基本类型的Boolean true或者false。属于JS BOM
                    if(confirmDelete)
                        return true;
                    return false;
                }
            })
        })
    </script>
</head>
<body>
