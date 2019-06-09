<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:main title="Unmoderated Posts">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Posts In Queue</h4>
                        <h6 class="card-subtitle">Showing all posts waited for moderation</h6>
                    </div>
                    <div class="table-responsive">
                        <table id="posts-table" class="table table-hover">
                            <tr>
                                <th>#</th>
                                <th>Channel</th>
                                <th>Text</th>
                                <th>Images</th>
                                <th>Creation time</th>
                            </tr>
                            <c:forEach items="${posts}" var="post">
                                <tr>
                                    <td><a href="/admin/moderation/posts/${post.id}/">${post.id}</a></td>
                                    <td>${post.channel}</td>
                                    <td><a href="/admin/moderation/posts/${post.id}/">${post.text}</a></td>
                                    <td>
                                        <c:forEach items="${post.images}" var="image">
                                            <img src="${image}" alt="${image}"/>
                                        </c:forEach>
                                    </td>
                                    <td>${post.createdDt}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:main>