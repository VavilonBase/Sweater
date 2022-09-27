<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "Main Page">
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="GET" action="/main">
                <input type="text" class="form-control" name="filter" value="${filter?ifExists}" placeholder="Введите фильтр(тег)">
                <button type="submit" class="btn btn-primary mt-3">Search</button>
            </form>
        </div>
    </div>

    <a class="btn btn-primary mt-3" data-bs-toggle="collapse" href="#collapseAddMessageForm" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>

    <div class="collapse" id="collapseAddMessageForm">
        <div class="form-group mt-3">
            <form method="POST" enctype="multipart/form-data">
                <div class="form-group mt-2">
                    <input type="text" name="text" class="form-control" placeholder="Введите текст"/>
                </div>
                <div class="form-group mt-2">
                    <input type="text" name="tag" class="form-control" placeholder="Введите тег"/>
                </div>
                <div class="form-group mt-2">
                    <input type="file" name="file" class="form-control"/>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="form-group mt-2">
                    <button type="submit" class="btn btn-primary">Add message</button>
                </div>
            </form>
        </div>
    </div>

    <div>Список сообщений:</div>
    <div class="row row-cols-1 row-cols-md-2 g-4">
        <#list messages as message>
            <div class="col">
                <div class="card my-3"">
                    <#if message.filename??>
                        <img class="card-img-top" src="/img/${message.filename}">
                    </#if>
                    <div class="m-2">
                        <span>${message.text}</span>
                        <i>${message.tag}</i>
                    </div>
                    <div class="card-footer">
                        <strong>${message.authorName}</strong>
                    </div>
                </div>
            </div>
        <#else>
            <h5>No messages</h5>
        </#list>
    </div>

</@c.page>
