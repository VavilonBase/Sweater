<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>
<#include "parts/security.ftlh">

<@c.page "Main Page">
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="GET" action="/main">
                <input type="text" class="form-control" name="filter" value="${filter?ifExists}" placeholder="Введите фильтр(тег)" />
                <button type="submit" class="btn btn-primary mt-3">Search</button>
            </form>
        </div>
    </div>
    
    <#include "parts/messageEdit.ftlh" />
    <#include "parts/messageList.ftlh" />

</@c.page>
