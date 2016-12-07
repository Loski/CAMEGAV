$(document).ready(function()
{
    $(".nullPointer").hide();

    console.log("OK");

    $("body").on("mouseenter","div.node.wordEnd",function()
    {
        $(this).addClass("highlight");

        var nodeToTest = $(this);

        for(var i=1;i<$(this).parents("tbody").length;i++)
        {
            var parent = $(this).parents("tbody").eq(i).find("div.node").eq(0);

            if(nodeToTest.hasClass("eqNode"))
            {
                parent.addClass("highlight");
            }

            nodeToTest = parent;
        }

        /*

        for(var i=1;i<$(this).parents("tbody").length;i++)
        {
            var previousParent = $(this);

            if(i>1)
                previousParent = $(this).parents("tbody").eq(i-1).find("div.node");

            var parent = $(this).parents("tbody").eq(i).find("div.node");

            console.log(parent.index(previousParent));

            if(parent.index(previousParent)==2)
                $(this).parents("tbody").eq(i).find("div.node").eq(0).css("background","red");
        }*/

    });

    $("body").on("mouseleave","div.node.wordEnd",function()
    {

        $(".highlight").removeClass("highlight");

        /*$(this).css("background","white");

        for(var i=1;i<$(this).parents("tbody").length;i++)
            $(this).parents("tbody").eq(i).find("div.node").eq(0).css("background","white");*/
    });

});