<#import "parts/common.ftlh" as c>
<#import "parts/loginForm.ftlh" as lf>

<@c.page "User List">
    User List

    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Roles</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">Edit user</a></td>
            </tr>
        </#list>
        </tbody>
    </table>

</@c.page>