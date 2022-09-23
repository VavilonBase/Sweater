<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Main Page">
    <@lf.logout/>
    <a href="/user">User List</a>
    <form method="POST" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите текст"/>
        <input type="text" name="tag" placeholder="Введите тег"/>
        <input type="file" name="file"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>

    <form method="GET">
        <input type="text" name="filter" value="${filter?ifExists}" placeholder="Введите фильтр(тег)">
        <button type="submit">Применить</button>
    </form>

    <div>Список сообщений:</div>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <#if message.filename??>
                <img src="/img/${message.filename}">
            </#if>
        </div>
    <#else>
        No Message
    </#list>>
</@c.page>
