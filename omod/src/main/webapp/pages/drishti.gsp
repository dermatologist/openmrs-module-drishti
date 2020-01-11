<% ui.decorateWith("appui", "standardEmrPage") %>

<p>Hello, Drishti user.</p>

<% if (context.authenticated) { %>
<p>And a special hello to you, $context.authenticatedUser.personName.fullName.</p>
<a href="/openmrs/owa/drishti/index.html">Click here to authenticate your cloud services.</a>
<br>
<br>
Your roles in the system are:
    <% context.authenticatedUser.roles.findAll { !it.retired }.each { %>
        $it.role ($it.description)
    <% } %>
<% } else { %>
    You are not logged in.
<% } %>

