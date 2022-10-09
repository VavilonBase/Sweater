<#import "parts/common.ftlh" as c>

<@c.page "Subscribers">
    <h3>${userChannel.username}</h3>
    <h3>Subscribers</h3>
    <#include "parts/userList.ftlh" />
</@c.page>