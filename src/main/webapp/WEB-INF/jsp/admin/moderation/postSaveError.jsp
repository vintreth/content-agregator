<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:main title="Ошибка сохранения">
    <jsp:attribute name="content">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Ошибка сохранения формы</h4>
                        <h6 class="card-subtitle">${errorDescription} - <code class="m-r-10">${errorCode}</code></h6>
                        <h6 class="card-subtitle"><a href="/admin/moderation/posts/${postId}/">Вернуться к посту</a></h6>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>
</t:main>