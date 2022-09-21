<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Main Page">
    <@lf.logout/>
    <form method="POST">
        <input type="text" name="text" placeholder="Введите текст"/>
        <input type="text" name="tag" placeholder="Введите тег"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>

    <form method="GET">
        <input type="text" name="filter" placeholder="Введите фильтр(тег)">
        <button type="submit">Применить</button>
    </form>

    <div>Список сообщений:</div>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
        </div>
    <#else>
        No Message
    </#list>>
</@c.page>
