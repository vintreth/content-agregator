<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="utf-8" />
      <title>Posts</title>
   </head>
   <body>
        <table id="posts_table">
            <tr>
                <th>Id</th>
                <th>Channel</th>
                <th>Text</th>
                <th>Images</th>
                <th>Creation time</th>
            </tr>
            <c:forEach items="${posts}" var="post">
                <tr>
                    <td>${post.id}</td>
                    <td>${post.channel}</td>
                    <td>${post.text}</td>
                    <td>
                        <c:forEach items="${post.images}" var="image">
                            <img src="${image}" alt="${image}"/>
                        </c:forEach>
                    </td>
                    <td>${post.createdDt}</td>
                </tr>
            </c:forEach>
        </table>
   </body>
</html>