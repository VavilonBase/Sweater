<#macro pager url page>
    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber gt 4)?then([1, -1], 1..3)
        tail = (pageNumber lt totalPages - 3)?then([-1, totalPages], totalPages-2..totalPages)
        bodyBefore = (pageNumber gt 4 && pageNumber lt totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber gt 2 && pageNumber lt totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])
        elem = (pageNumber gt 3 && pageNumber lt totalPages - 2)?then([pageNumber], [])
        body = head + bodyBefore + elem + bodyAfter + tail
        />
        <#else>
            <#assign
            body = 1..page.getTotalPages()
            />
    </#if>
    <div class="mt3 container">
        <div class="row">
            <ul class="pagination col justify-content-center">
                <li class="page-item"><a class="page-link disabled" href="#">Страницы</a></li>
                <#list body as p>
                    <#if p == -1>
                        <li class="page-item disabled"><a class="page-link" href="#">...</a></li>
                    <#else>
                        <#if p - 1 == page.getNumber()>
                            <li class="page-item active"><a class="page-link" href="#">${p}</a></li>
                        <#else>
                            <li class="page-item"><a class="page-link" href="${url}?page=${p-1}&size=${page.getSize()}">${p}</a></li>
                        </#if>
                    </#if>

                </#list>
            </ul>
            <ul class="pagination col justify-content-center">
                <li class="page-item"><a class="page-link disabled" href="#">Количество элементов на странице</a></li>
                <#list [5, 10, 30, 50] as size>
                    <#if size == page.getSize()>
                        <li class="page-item active"><a class="page-link" href="#">${size}</a></li>
                    <#else>
                        <li class="page-item"><a class="page-link" href="${url}?page=${page.getNumber()}&size=${size}">${size}</a></li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>
</#macro>