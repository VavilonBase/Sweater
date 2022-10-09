<a class="btn btn-primary mt-3" data-bs-toggle="collapse" href="#collapseAddMessageForm" role="button" aria-expanded="false" aria-controls="collapseExample">
    Message Edit
</a>

<div class="collapse <#if message??>show</#if>" id="collapseAddMessageForm">
    <div class="form-group mt-3">
        <form method="POST" enctype="multipart/form-data">
            <div class="form-group mt-2">
                <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>" placeholder="Введите текст"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>

            <div class="form-group mt-2">
                <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.tag}</#if>" placeholder="Введите тег"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group mt-2">
                <input type="file" name="file" class="form-control"/>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group mt-2">
                <button type="submit" class="btn btn-primary">Save Message</button>
            </div>
        </form>
    </div>
</div>