<%@ tag language="java" %>
<%@ attribute name="baseUrl" required="true" %>
<%@ attribute name="pages" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-12">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <c:forEach items="${pages}" var="page">
                    <c:if test="${page.first}">
                        <li class="page-item">
                            <a class="page-link" href="${baseUrl}/${page.num}/" aria-label="First">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">First</span>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${!page.first && !page.last}">
                        <li class="page-item <c:if test="${page.current}">active</c:if>">
                            <a class="page-link" href="${baseUrl}/${page.num}/">${page.num}</a>
                        </li>
                    </c:if>
                    <c:if test="${page.last}">
                        <li class="page-item">
                            <a class="page-link" href="${baseUrl}/${page.num}/" aria-label="Last">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Last</span>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>