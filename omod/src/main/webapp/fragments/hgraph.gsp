<%
    ui.includeCss("drishti", "HealthGraph.css")

%>
<% ui.includeJavascript("drishti", "d3.js") %>
<% ui.includeJavascript("drishti", "HealthGraph.js") %>

<script>
    var jq = jQuery;
    jq(document).ready(function () {
        jq("#tabs").tabs();
        jq("#but_plan").click(function (e) {
            jq.get("/plan/application/user/${uuid}", function (data) {

            });
        });
        jq("#but_act").click(function (e) {
            window.location.href = "/act/index.html#/user/${uuid}";
        });


    });

</script>

<script>
    (function () {
        // Mean value passed by the controller
        var gender = '${ patient.gender }';
        var glucose = 120;
        var weight = 74;
        var steps = ${steps};

        jQuery(document).ready(function () {
            jQuery.ajax({
                method: 'get',
                beforeSend: function (xhr) {
                    var token = jQuery("meta[name='csrf-token']").attr("content");
                    xhr.setRequestHeader("X-CSRF-Token", token);
                },
                url: '/openmrs/ms/uiframework/resource/drishti/data/metrics.json',
                dataType: 'json',
                async: true,
                complete: function (jqXHR) {
                    //console.log('fillData complete, jqXHR readyState is ' + jqXHR.readyState);
                    if (jqXHR.readyState === 4) {
                        //console.log('jqXHR readyState = 4');
                        if (jqXHR.status == 200) {

                            var randomBetween = function (min, max) {
                                if (min < 0) {
                                    return min + Math.random() * (Math.abs(min) + max);
                                } else {
                                    return min + Math.random() * max;
                                }
                            };

                            var str = jqXHR.responseText; //metrics json
                            //console.log('str = ' + str);
                            var json = jQuery.parseJSON(str);
                            var factors_array = [];
                            var factor_json;
                            var cholesterol = {
                                label: 'Total Cholesterol',
                                score: 0,
                                value: 0,
                                actual: 0,
                                weight: 0,
                                details: []
                            };
                            var bp = {
                                label: 'Blood Pressure',
                                score: 0,
                                value: 0,
                                actual: 0,
                                weight: 0,
                                details: []
                            };
                            if (json[0].gender === gender)
                                factor_json = json[0].metrics;
                            else
                                factor_json = json[1].metrics;
                            //console.log(factor_json);
                            for (var i = 0; i < factor_json.length; i++) {
                                var random = randomBetween(factor_json[i].features.totalrange[0], factor_json[i].features.totalrange[1]);
                                //console.log(factor_json[i].name);
                                //console.log(factor_json[i].features);
                                console.log(random);
                                if ((factor_json[i].name === 'LDL' || factor_json[i].name === 'HDL' || factor_json[i].name === 'Triglycerides') && cholesterol != null) {
                                    cholesterol.details.push({
                                        label: factor_json[i].name,
                                        score: HGraph.prototype.calculateScoreFromValue(factor_json[i].features, random),
                                        value: parseFloat(random).toFixed(2) + ' ' + factor_json[i].features.unitlabel,
                                        actual: random,
                                        weight: factor_json[i].features.weight
                                    });
                                    if (cholesterol.details.length >= 3) {
                                        for (var j = 0; j < cholesterol.details.length; j++) {
                                            cholesterol.score = cholesterol.score + cholesterol.details[j].score;
                                            cholesterol.actual = cholesterol.actual + cholesterol.details[j].actual;
                                            cholesterol.weight = cholesterol.weight + cholesterol.details[j].weight
                                        }
                                        cholesterol.score /= 3;
                                        cholesterol.weight /= 3;
                                        cholesterol.value = parseFloat(cholesterol.actual).toFixed(2) + ' ' + factor_json[i].features.unitlabel;
                                        factors_array.push(cholesterol);
                                        cholesterol = null
                                    }
                                } else if ((factor_json[i].name === 'Blood Pressure Diastolic' || factor_json[i].name === 'Blood Pressure Systolic') && bp != null) {
                                    bp.details.push({
                                        label: factor_json[i].name.replace('Blood Pressure ', ''),
                                        score: HGraph.prototype.calculateScoreFromValue(factor_json[i].features, random),
                                        value: parseFloat(random).toFixed(2) + ' ' + factor_json[i].features.unitlabel,
                                        weight: factor_json[i].features.weight,
                                        actual: random
                                    });
                                    if (bp.details.length >= 2) {
                                        console.log(bp.score);
                                        for (var j = 0; j < bp.details.length; j++) {
                                            bp.score = bp.score + bp.details[j].score;
                                            bp.weight = bp.weight + bp.details[j].weight;
                                        }
                                        bp.score /= 2;
                                        bp.weight /= 2;
                                        bp.value = parseFloat(bp.details[0].actual).toFixed(2) + '/' + parseFloat(bp.details[1].actual).toFixed(2) + ' ' + factor_json[i].features.unitlabel;
                                        factors_array.push(bp);
                                        bp = null
                                    }
                                } else if (factor_json[i].name === 'Glucose') //Mean value added
                                {
                                    factors_array.push(
                                        {
                                            label: factor_json[i].name,
                                            score: HGraph.prototype.calculateScoreFromValue(factor_json[i].features, glucose),
                                            value: parseFloat(glucose).toFixed(2) + ' ' + factor_json[i].features.unitlabel,
                                            weight: factor_json[i].features.weight
                                        }
                                    )
                                } else if (factor_json[i].name === 'Exercise') //Mean value added
                                {
                                    factors_array.push(
                                        {
                                            label: factor_json[i].name,
                                            score: HGraph.prototype.calculateScoreFromValue(factor_json[i].features, steps),
                                            value: parseFloat(steps).toFixed(2) + ' ' + factor_json[i].features.unitlabel,
                                            weight: factor_json[i].features.weight
                                        }
                                    )
                                } else
                                    factors_array.push(
                                        {
                                            label: factor_json[i].name,
                                            score: HGraph.prototype.calculateScoreFromValue(factor_json[i].features, random),
                                            value: parseFloat(random).toFixed(2) + ' ' + factor_json[i].features.unitlabel,
                                            weight: factor_json[i].features.weight
                                        }
                                    )
                            }
                            var opts = {
                                container: document.getElementById("viz"),
                                userdata: {
                                    hoverevents: true,
                                    factors: factors_array
                                },
                                showLabels: true
                            };
                            console.log(opts);
                            graph = new HGraph(opts);
                            graph.width = 300;
                            graph.height = 250;
                            graph.initialize();
                        }
                    }
                }
            });
        });
    })();
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
                <div>
                    Gender: ${patient.gender} |
                    Steps: ${steps} |
                    UUID: ${uuid}
                </div>
                <figure id="viz" class="content_inset healthgraph detailed"></figure>

                <div id="responds"></div>
            </div>


            <div id="careplan-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">
                <a class="button" id="but_plan">
                    <i class="icon-upload-alt"></i>Plan
                </a>
                <a class="button" id="but_act">
                    <i class="icon-download-alt"></i>Act
                </a>

            </div>

            <div id="help-tab" class="ui-tabs-panel ui-widget-content ui-corner-bottom">

            </div>
        </small>
    </div> <!--tabs-->
</div><!--hgraph-main-->
