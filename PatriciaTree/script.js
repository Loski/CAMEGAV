$(document).ready(function()
{
    $(".nullPointer").hide();

    console.log("OK");

    $("body").on("mouseenter","div.node.wordEnd",function(event )
    {
        $(this).addClass("highlight");

        var nodeToTest = $(this);
        var word =$(this).text();

        for(var i=1;i<$(this).parents("tbody").length;i++)
        {
            var parent = $(this).parents("tbody").eq(i).find("div.node").eq(0);

            if(nodeToTest.hasClass("eqNode"))
            {
                parent.addClass("highlight");
                word+=parent.find('h2').text();
            }

            nodeToTest = parent;
        }

        $("body").append("<div id=\"wordDisplay\"></div>");
        $("#wordDisplay").css("position","absolute");
        $("#wordDisplay").css("font-size","5em");
        $("#wordDisplay").css("top",event.pageY);
        $("#wordDisplay").css("background","white");
        $("#wordDisplay").css("left",event.pageX);
        
        $("#wordDisplay").text(word.split("").reverse().join(""));

    });

    $("body").on("mouseleave","div.node.wordEnd",function()
    {
        $(".highlight").removeClass("highlight");
    });

});