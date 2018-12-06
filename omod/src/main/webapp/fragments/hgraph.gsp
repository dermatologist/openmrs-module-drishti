<%
    ui.includeCss("drishti", "drishti.css")
%>
<script>
    var jq = jQuery;
    jq(document).ready(function () {
        jq("#tabs").tabs();

    });
</script>

<!-- Title begins here -->
<div id="drishti-main" class="info-section drishti">
    <div class="info-header">
        <i class="icon-pencil"></i>

        <h3>DRISHTI</h3>
    </div>


    <div id="tabs" class="ui-tabs">
        <ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" role="tablist">
            <li class="ui-state-default ui-corner-top ui-tabs-active ui-state-active">
                <a href="#hgraph-tab" class="ui-tabs-anchor">
                    hGraph
                </a>
            </li>
            <li class="ui-state-default ui-corner-top">
                <a href="#careplan-tab" class="ui-tabs-anchor">
                    Care Plan
                </a>
            </li>
            <li class="ui-state-default ui-corner-top">
                <a href="#help-tab" class="ui-tabs-anchor">
                    Help
                </a>
            </li>
        </ul>

        <!-- Title Ends here -->
        <small>
            <div id="hgraph-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">

            </div>


            <div id="careplan-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">

            </div>

            <div id="help-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">

            </div>
        </small>
    </div> <!--tabs-->
</div><!--hgraph-main-->
