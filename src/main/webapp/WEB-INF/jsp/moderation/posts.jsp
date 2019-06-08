<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
   <head>
      <meta charset="utf-8" />
      <title>Posts</title>
   </head>
   <body>
        <table>
            <tr>
                <th>Id</th>
                <th>Channel</th>
                <th>Text</th>
                <th>Images</th>
                <th>Creation time</th>
            </tr>
            <c:forEach item="posts" var="post">
                <tr>
                    <td>${post.id}</td>
                    <td>${post.channelId}</td>
                    <td>${post.text}</td>
                    <td>img</td>
                    <td>${post.creationDt}</td>
                </tr>
            </c:forEach>
        </table>
   </body>
</html>