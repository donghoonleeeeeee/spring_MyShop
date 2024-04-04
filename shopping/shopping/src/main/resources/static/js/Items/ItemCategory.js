function category_check()
{
    var code = document.getElementById("modify_code").value;
    if(code.length == 2)
    {
        if(confirm("대분류는 삭제 시 중분류와 소분류도 함께 삭제됩니다.\n계속 하시겠습니까?") == true)
        {
            alert("삭제되었습니다.");
        }
        else
        {
            alert("중지합니다.");
            return false;
        }
    }
    else if(code.length == 4)
    {
        if(confirm("중분류는 삭제 시 소분류도 함께 삭제됩니다.\n계속 하시겠습니까?") == true)
        {
            alert("삭제되었습니다.");
        }
        else
        {
            alert("중지합니다.");
            return false;
        }
    }
    else
    {
        if(confirm("삭제하시겠습니까?") == true)
        {
            alert("삭제되었습니다.");
        }
        else
        {
            alert("중지합니다.");
            return false;
        }
    }
}

function set_second(code, id, name)
{
    var second_area = document.getElementById("second");
    var third_area = document.getElementById("third");
    $(".first_class").css("fontWeight","normal");
    document.getElementById(id).style.fontWeight = "bold";

        $.ajax({
            url : '/admin/SetLabel',
            data : {code : code},
            type : 'POST',
            dataType : 'json',
            success : function(result){
                $("#second").empty();
                $("#modify_second").empty();
                for(var a=0; a<result.length; a++)
                {
                    if(result[a].split("/")[0].length == 4)
                    {
                        var second_data = document.createElement("span");
                        var br = document.createElement("br");

                        second_data.innerHTML = result[a].split("/")[1];
                        second_data.id = result[a];
                        second_data.className = 'second_class';
                        second_data.onclick = function(){set_third(this.id)};


                        second_area.append(second_data);
                        second_area.append(document.createElement("br"));
                        second_area.append(document.createElement("br"));
                    }
                }
                second_area.style.display = "inline";
                third_area.style.display = "none";
            }
        });
        document.getElementById("modify_code").value = code;
        document.getElementById("modify_name").value = name;
}

function set_third(value) // value : 1010/한국, 1020/일본 ... (code + name)
{
    $(".second_class").css("fontWeight","normal");
    document.getElementById(value).style.fontWeight = "bold";
    var code = value.split("/")[0];
    var name = value.split("/")[1];

    var third_area = document.getElementById("third");

    $.ajax({
        url : '/admin/SetLabel',
        data : {code : code},
        type : 'POST',
        dataType : 'json',
        success : function(result){
            $("#third").empty();
            for(var a=0; a<result.length; a++)
            {
                if(result[a].split("/")[0].length == 6)
                {

                    var third_data = document.createElement("span");
                    var br = document.createElement("br");

                    third_data.innerHTML = result[a].split("/")[1];
                    third_data.id = result[a];
                    third_data.className = 'third_class';
                    third_data.onclick = function(){set_data(this.id)}

                    third_area.append(third_data);
                    third_area.append(document.createElement("br"));
                    third_area.append(document.createElement("br"));
                }
            }
            third_area.style.display = "inline";
        }
        });
        document.getElementById("modify_code").value = code;
        document.getElementById("modify_name").value = name;
}

function set_data(value)
{
    document.getElementById(value).style.fontWeight = "bold";
    var code = value.split("/")[0];
    var name = value.split("/")[1];
    document.getElementById("modify_code").value = code;
    document.getElementById("modify_name").value = name;
}

function change_label1(value)
{
    var select2 = document.getElementById("category2");


    var basic_option1 = document.createElement("option");
    var basic_option2 = document.createElement("option");

    if(value == "none")
    {
        $("#category2").empty();
        document.getElementById("category2").style.display = "none";
        document.getElementById("category3").style.display = "none";

        document.getElementById("label").value = "none";
    }
    else if(value == "category1")
    {
        document.getElementById("category2").value = "none";
        document.getElementById("category2").style.display = "none";

        document.getElementById("category3").value = "none";
        document.getElementById("category3").style.display = "none";

        $.ajax({
        url : '/admin/LabelCheck',
        data : {category : value},
        type : 'POST',
        dataType : 'json',
        success : function(result){
        document.getElementById("code").value = result;
        $("#category2").empty();
        }
        });
    }
    else
    {
        $.ajax({
        url : '/admin/SetLabel',
        data : {code : value},
        type : 'POST',
        dataType : 'json',
        success : function(result){
                $("#category2").empty();

                basic_option1.text = "선택";
                basic_option1.value = "none";
                select2.add(basic_option1);

                basic_option2.text = "2차 분류";
                basic_option2.value = "category2";
                select2.add(basic_option2);

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
                document.getElementById("code").value = value;
                document.getElementById("category2").style.display = "inline";
        }
        });
    }
}

function change_label2(value)
{
    var first = document.getElementById("category1").value;

    var select = document.getElementById("category3");
    var option = document.createElement("option");

    var basic_option1 = document.createElement("option");
    var basic_option2 = document.createElement("option");

    if(value == "none")
    {
        $("#category3").empty();
        document.getElementById("category3").style.display = "none";
        document.getElementById("label").value = first;
    }
    else if(value == "category2")
    {
        $("#category3").empty();
        document.getElementById("category3").style.display = "none";

        $.ajax({
        url : '/admin/LabelCheck',
        data : {category : value, info : first},
        type : 'POST',
        dataType : 'json',
        success : function(result){
            document.getElementById("code").value = result;
        }
        });
    }
    else
    {
        $.ajax({
        url : '/admin/SetLabel',
        data : {code : value},
        type : 'POST',
        dataType : 'json',
        success : function(result){

                $("#category3").empty();

                basic_option1.text = "선택";
                basic_option1.value = "none";
                select.add(basic_option1);

                basic_option2.text = "3차 분류";
                basic_option2.value = "category3";
                select.add(basic_option2);

                document.getElementById("code").value = value;
                document.getElementById("category3").style.display = "inline";
        }
        });
    }
}

function change_label3(value)
{
    var first = document.getElementById("category1").value;
    var second = document.getElementById("category2").value;

    if(value == "none")
    {
        document.getElementById("label").value = second;
    }
    else if(value == "category3")
    {
        $.ajax({
        url : '/admin/LabelCheck',
        data : {category : value, info : second},
        type : 'POST',
        dataType : 'json',
        success : function(result){
            document.getElementById("code").value = result;
        }
        });
    }
}