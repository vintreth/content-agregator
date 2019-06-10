<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:main title="Посты на модерации">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Посты в очереди модерации</h4>
                        <h6 class="card-subtitle">Отображение всех постов, ожидающих проверки</h6>
                    </div>
                    <div class="table-responsive">
                        <table id="posts-table" class="table table-hover">
                            <tr>
                                <th>#</th>
                                <th>Канал</th>
                                <th>Текст</th>
                                <th>Изображение</th>
                                <th>Время создания</th>
                            </tr>
                            <c:forEach items="${posts}" var="post">
                                <tr>
                                    <td><a href="/admin/moderation/posts/${post.id}/">${post.id}</a></td>
                                    <td>${post.channel}</td>
                                    <td><a href="/admin/moderation/posts/${post.id}/">${post.text}</a></td>
                                    <td>
                                        <c:if test="${post.imagesSize > 0}">
                                            <span class="label label-success label-rounded">Да</span>
                                        </c:if>
                                        <c:if test="${post.imagesSize <= 0}">
                                            <span class="label label-danger label-rounded">Нет</span>
                                        </c:if>
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