<#import "parts/common.ftlh" as c>

<@c.page "Login">
    <h5 class="mb-3">Profile</h5>
    ${message?ifExists}

    <form action="/user/profile" method="post">
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="text" name="password" class="form-control" id="password" placeholder="Input password" />
        </div>
            <div class="mb-3">
                <label for="email" class="form-label">email</label>
                <input type="email" name="email" value="${email!''}" class="form-control" id="email" placeholder="Input email" />
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>