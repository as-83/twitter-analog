<#import "parts/common.ftl" as c>

<@c.page>
<h2>User Editor</h2>

    <form action="/user" method="post">
        <div><label> User Name : <input type="text" name="username" value="${user.username}"/> </label></div>
        <#list roles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>

        </#list>
        <div><label> Id: <input type="hidden" name="userId" value="${user.id}"/> </label></div>
        <input type="hidden" value="${_csrf.token}" name="_csrf" />
        <button type="submit">Save</button>
    </form>


</@c.page>
