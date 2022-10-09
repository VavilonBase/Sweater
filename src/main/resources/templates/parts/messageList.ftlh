<div>Список сообщений:</div>
<div class="row row-cols-1 row-cols-md-2 g-4">
    <#list messages as message>
        <div class="col">
            <div class="card my-3">
                <#if message.filename??>
                    <img class="card-img-top" src="/img/${message.filename}">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <br>
                    <i>#${message.tag}</i>
                </div>
                <div class="card-footer">
                    <a href="/user-messages/${message.author.id}">
                        <strong>${message.authorName}</strong>
                    </a>
                    <#if currentUserId == message.author.id>
                        <a href="/user-messages/${message.author.id}?message=${message.id}"
                           class="btn btn-primary">Edit</a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        <h5>No messages</h5>
    </#list>
</div>