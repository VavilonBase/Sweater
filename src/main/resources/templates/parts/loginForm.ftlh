<#macro login path isRegistration>
    <form action="${path}" method="post">
        <div class="form-group mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   id="username" placeholder="Input username" value="<#if user??>${user.username}</#if>">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="text" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   id="password" placeholder="Input password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegistration>
            <div class="form-group mb-3">
                <label for="password2" class="form-label">Password confirm</label>
                <input type="text" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       id="password" placeholder="Input password">
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
            <div class="form-group mb-3">
                <label for="email" class="form-label">email</label>
                <input type="email" name="email" class="form-control ${(emailError??)?string('is-invalid', '')}"
                       id="email" placeholder="Input email" value="<#if user??>${user.email}</#if>">
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
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