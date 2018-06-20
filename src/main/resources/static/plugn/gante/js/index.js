var demo_tasks = {
    "data": [   
        {
         "id": 11, 
         "text": "任务一", 
         "start_date": "28-03-2018 08:00",
         "end_date":"28-03-2018 18:00",
//         "parent":"1",
         "duration": 3,
         "progress": 1, 
         "open": true
        },
        {
            "id":12,
            "text":"任务二",
            "start_date":"29-03-2018 09:00",
            "end_date":"29-03-2018 18:00",
            "duration":1,
            "progress":1,
            "open":true
        }

    ],
    "links":[
        {
         "id":"11",
         "source":"11",
         "target":"12",
         "type":"1"
        }
    ]
};


gantt.config.columns = [
    {name:"text",       label:"任务",  width:"*", tree:true },
    {name:"start_date", label:"开始时间", align: "center", width:"*" },
    {name:"duration",   label:"持续时间", align: "center", width:70 },
    {name:"add",  width:44 }
];


gantt.attachEvent("onTemplatesReady", function () {
    var toggle = document.createElement("i");
    toggle.className = "fa fa-expand gantt-fullscreen";
    gantt.toggleIcon = toggle;
    gantt.$container.appendChild(toggle);
    toggle.onclick = function () {
        if (!gantt.getState().fullscreen) {
            gantt.expand();
        }
        else {
            gantt.collapse();
        }
    };
});
gantt.attachEvent("onExpand", function () {
    var icon = gantt.toggleIcon;
    if (icon) {
        icon.className = icon.className.replace("fa-expand", "fa-compress");
    }

});
gantt.attachEvent("onCollapse", function () {
    var icon = gantt.toggleIcon;
    if (icon) {
        icon.className = icon.className.replace("fa-compress", "fa-expand");
    }
});




