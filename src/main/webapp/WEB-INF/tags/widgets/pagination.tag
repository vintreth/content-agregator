<%@ tag language="java" %>
<%@ attribute name="baseUrl" required="true" %>
<%@ attribute name="pages" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="row">
                <div class="col-4"></div>
                <div class="col-4">
                    <div class="row">
                        <c:forEach items="${pages}" var="page">
                            <div class="col-2"><a href="${baseUrl}/${page.num}/">${page.text}</a></div>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-4"></div>
            </div>
        </div>
    </div>
</div>