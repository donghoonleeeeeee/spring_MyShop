function search_item()
{
    var category1 = document.getElementById("category1").value;
    var category2 = document.getElementById("category2").value;
    var category3 = document.getElementById("category3").value;
    var category = category1 + "/" + category2 + "/" + category3;
    var keyword = document.getElementById("keyword").value;

    location.href='/User/ItemList?category='+category+'&keyword='+keyword;
}

function set_category2(value)
{
    var select2 = document.getElementById("category2");
    var select3 = document.getElementById("category3");

    var basic_option1 = document.createElement("option");
    var basic_option2 = document.createElement("option");

    if(value == "none")
    {
        $("#category2").empty();
        $("#category3").empty();

        basic_option1.text = "선택";
        basic_option1.value = "none";
        select2.add(basic_option1);

        basic_option2.text = "선택";
        basic_option2.value = "none";
        select3.add(basic_option2);
    }
    else
    {
        $.ajax({
            url : '/User/SetLabel',
            data : {code : value},
            type : 'POST',
            dataType : 'json',
            success : function(result){
            $("#category2").empty();

            basic_option1.text = "선택";
            basic_option1.value = "none";
            select2.add(basic_option1);

            for(var a=0; a<result.length; a++)
            {
                if(result[a].split("/")[0].length == 4)
                    {
                        var option = document.createElement("option");
                        option.text = result[a].split("/")[1];
                        option.value = result[a].split("/")[0];
                        select2.add(option);
                    }
            }
            }
        });
    }
}

function set_category3(value)
{
    var first = document.getElementById("category1").value;

    var select = document.getElementById("category3");
    var option = document.createElement("option");

    var basic_option1 = document.createElement("option");
    var basic_option2 = document.createElement("option");

    if(value == "none")
    {
        $("#category3").empty();

        basic_option2.text = "선택";
        basic_option2.value = "none";
        select3.add(basic_option2);
    }
    else
    {
        $.ajax({
        url : '/User/SetLabel',
        data : {code : value},
        type : 'POST',
        dataType : 'json',
        success : function(result){

        $("#category3").empty();

        basic_option1.text = "선택";
        basic_option1.value = "none";
        select.add(basic_option1);

        for(var a=0; a<result.length; a++)
        {
            if(result[a].split("/")[0].length == 6)
            {
                var option = document.createElement("option");
                option.text = result[a].split("/")[1];
                option.value = result[a].split("/")[0];
                select.add(option);
            }
        }
        }
        });
    }
}