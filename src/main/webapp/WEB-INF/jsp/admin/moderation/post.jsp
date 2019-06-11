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
                            <div class="form-group row p-t-20">
                                <div class="col-sm-4">
                                    <c:forEach items="${post.images}" var="image" varStatus="counter">
                                        <div class="custom-control custom-radio">
                                            <input type="radio" id="image-${counter.count}" name="customRadio" value="${counter.count}" class="custom-control-input">
                                            <label class="custom-control-label" for="image-${counter.count}">${image.title}</label>
                                            <a href="${image.src}"><img src="${image.src}" alt="${image.title}" width="480"/></a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Канал</label>
                                <select class="custom-select col-12" id="inlineFormCustomSelect" name="channelId">
                                    <option value="${post.channelId}" selected>${post.channel}</option>
                                    <c:forEach items="${channels}" var="channel">
                                        <option value="${channel.id}" selected>${channel.name}</option>
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