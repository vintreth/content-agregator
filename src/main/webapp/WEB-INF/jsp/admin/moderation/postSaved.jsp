<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:main title="Изменения сохранены">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Изменения сохранены</h4>
                        <h6 class="card-subtitle"><a href="/admin/moderation/posts/page/1/">Вернуться к списку</a></h6>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:main>