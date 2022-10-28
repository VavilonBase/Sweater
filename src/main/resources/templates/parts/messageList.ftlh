<#import "pager.ftlh" as p>

<div>Список сообщений:</div>
<div class="row row-cols-1 row-cols-md-2 g-4" id="messageList">
    <@p.pager url page/>
    <#list page.getContent() as message>
        <div class="col">
            <div class="col card my-3" data-id="${message.id}">
                <#if message.filename??>
                    <img class="card-img-top" src="/img/${message.filename}" />
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <br />
                    <i>#${message.tag}</i>
                </div>
                <div class="card-footer container">
                    <div class="row">
                        <a class="col" href="/user-messages/${message.author.id}">
                            <strong>${message.authorName}</strong>
                        </a>
                        <a href="/messages/${message.id}/like">
                            <#if message.meLiked>
                                <i class="fas fa-heart"></i>
                            <#else>
                                <i class="far fa-heart"></i>
                            </#if>
                            ${message.likes}
                        </a>
                        <#if currentUserId == message.author.id>
                            <a href="/user-messages/${message.author.id}?message=${message.id}"
                               class="col btn btn-primary">Edit</a>
                        </#if>
                    </div>

                </div>
            </div>
        </div>
    <#else>
        <h5>No messages</h5>
    </#list>
</div>