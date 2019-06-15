<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:main title="Пост #${post.id}">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Пост #${post.id}</h4>
                        <h6 class="card-subtitle">Отредактируйте пост, если требуется или отклоните</h6>
                        <form class="form-horizontal m-t-30" id="post-form" method="POST" action="/admin/moderation/posts/${post.id}/">
                            <div class="form-group row p-t-20">
                                <div class="col-sm-4">
                                    <div class="custom-control custom-checkbox">
                                        <input name="publish" type="checkbox" class="custom-control-input" id="customCheck1">
                                        <label class="custom-control-label" for="customCheck1">Опубликовать</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Заголовок поста</label>
                                <input name="title" type="text" class="form-control" value="${post.title}">
                            </div>
                            <div class="form-group">
                                <label>Текст поста</label>
                                <textarea name="text" class="form-control" rows="5">${post.text}</textarea>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <c:forEach items="${post.images}" var="image">
                                        <tr>
                                            <c:forEach items="${image.sizes}" var="size">
                                                <td>
                                                    <div class="custom-control custom-checkbox">
                                                        <input type="checkbox" class="custom-control-input" id="size-${size.uuid}" name="${size.uuid}" value="imageSize">
                                                        <label class="custom-control-label" for="size-${size.uuid}">${size.title}</label>
                                                        <a href="${size.src}"><img src="${size.src}" alt="${size.title}" width="200"/></a>
                                                    </div>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                            <div class="form-group">
                                <label>Канал</label>
                                <select class="custom-select col-12" id="inlineFormCustomSelect" name="channelId">
                                    <option value="${post.channelId}" selected>${post.channel}</option>
                                    <c:forEach items="${channels}" var="channel">
                                        <option value="${channel.id}">${channel.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Время создания</label>
                                <input class="form-control" type="text" value="${post.createdDt}" readonly>
                            </div>
                            <div class="form-group">
                                <button type="submit" form="post-form" class="btn btn-success">Сохранить</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:main>