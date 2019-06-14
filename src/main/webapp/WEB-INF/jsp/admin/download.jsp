<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:main title="Ручная загрузка">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Ручная загрузка постов</h4>
                        <h6 class="card-subtitle"></h6>
                        <form class="form-horizontal m-t-30" id="download-form" method="POST" action="/admin/download/">
                            <div class="form-group">
                                <label>Источник</label>
                                <select class="custom-select col-12" id="source-select" name="sourceId">
                                    <c:forEach items="${sources}" var="source">
                                        <option value="${source.id}">${source.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Канал</label>
                                <select class="custom-select col-12" id="channel-select" name="channelId">
                                    <c:forEach items="${channels}" var="channel">
                                        <option value="${channel.id}">${channel.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Количество постов</label>
                                <input name="limit" type="text" class="form-control" value="5"/>
                            </div>
                            <div class="form-group">
                                <button type="submit" form="download-form" class="btn btn-success">Загрузить</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:main>