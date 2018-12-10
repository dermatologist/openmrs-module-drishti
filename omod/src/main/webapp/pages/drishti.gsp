<% ui.decorateWith("appui", "standardEmrPage") %>

Hello, Drishti user.

<% if (context.authenticated) { %>
    And a special hello to you, $context.authenticatedUser.personName.fullName.
<a href="/owa/drishti/index.html">Click here to authenticate your cloud services.</a>

Your roles in the system are:
    <% context.authenticatedUser.roles.findAll { !it.retired }.each { %>
        $it.role ($it.description)
    <% } %>
<% } else { %>
    You are not logged in.
<% } %>

${ ui.includeFragment("drishti", "users") }