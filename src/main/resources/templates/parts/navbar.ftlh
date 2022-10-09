<#include 'security.ftlh'>
<#import 'loginForm.ftlh' as lf>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Sweater</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link " href="/">Home</a>
                </li>
                <#if user??>
                    <li class="nav-item">
                        <a class="nav-link" href="/main">Messages</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user-messages/${currentUserId}">User messages</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">User List</a>
                    </li>
                </#if>
                <#if user??>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/profile">Profile</a>
                    </li>
                </#if>
            </ul>

            <div class="navbar-text mr-3">${name}</div>
            <#if know>
                <@lf.logout/>
            <#else>
                <a href="/login" class="btn btn-primary">Sign In</a>
            </#if>
        </div>
    </div>
</nav>