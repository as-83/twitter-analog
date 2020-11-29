<#import "parts/common.ftl" as c>

<@c.page>
    <#if userChannel??>
        <h3>${userChannel.username}</h3>
        <div>${type}</div>
    <#else>
        <h3>Message likes list</h3>
    </#if>

    <ul class="list-group">

        <#list users as user>
            <li class="list-group-item">
                <a href="/user-messages/${user.id}"> ${user.username}</a>
            </li>
        </#list>

    </ul>

</@c.page>
