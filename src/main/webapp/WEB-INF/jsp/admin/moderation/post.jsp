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
                        <form class="form-horizontal m-t-30">
                            <div class="form-group">
                                <label>Текст поста</label>
                                <textarea class="form-control" rows="5">${post.text}</textarea>
                            </div>
                            <div class="form-group">
                                <label>Канал</label>
                                <select class="custom-select col-12" id="inlineFormCustomSelect">
                                    <option selected>${post.channel}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Время создания</label>
                                <input class="form-control" type="text" placeholder="${post.createdDt}" readonly>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:main>