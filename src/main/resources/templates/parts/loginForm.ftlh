<#macro login path isRegistration>
    <form action="${path}" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" name="username" class="form-control" id="username" placeholder="Input username">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="text" name="password" class="form-control" id="password" placeholder="Input password">
        </div>
        <#if isRegistration>
            <div class="mb-3">
                <label for="email" class="form-label">email</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="Input email">
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">
            <#if isRegistration>
                Create
            <#else>
                Sign In
            </#if>
        </button>
        <#if !isRegistration>
            <a href="/registration" class="btn btn-danger">Sign up</a>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="submit" class="btn btn-danger" value="Sign out" />
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</#macro>