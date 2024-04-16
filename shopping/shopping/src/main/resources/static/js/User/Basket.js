
function change_box(count, item_idx)
{
    if(document.getElementById("main_checkbox"+count).value == "none")
    {
        document.getElementById("main_checkbox"+count).value = item_idx;
    }
    else
    {
        document.getElementById("main_checkbox"+count).value = "none";
    }
}
function set_result(count, id)
{
    if(document.getElementById(id).checked == true)
    {
        document.getElementById("total_price").innerHTML = parseInt(document.getElementById("total_price").innerHTML) + parseInt(document.getElementById("price"+count).innerHTML);
    }
    else if(document.getElementById(id).checked == false)
    {
        document.getElementById("total_price").innerHTML = parseInt(document.getElementById("total_price").innerHTML) - parseInt(document.getElementById("price"+count).innerHTML);
    }
}
function counted(id, count, price)
{
    var value = parseInt(document.getElementById("price_tag"+count).value);
    if(id == "count_up")
    {
        document.getElementById("price_tag"+count).value = value + 1;
        document.getElementById("price"+count).innerHTML = parseInt(price) * (value + 1);
        document.getElementById("content_quantity"+count).innerHTML = value + 1;
        if(document.getElementById("sub_checkbox"+count).checked == true)
        {
            document.getElementById("total_price").innerHTML = parseInt(document.getElementById("total_price").innerHTML) + parseInt(price);
        }
    }
    else if(id == "count_down")
    {
        if(value < 2)
        {
            document.getElementById("price_tag"+count).value = 1;
            document.getElementById("price"+count).innerHTML = price;
            document.getElementById("content_quantity"+count).innerHTML = 1;
        }
        else
        {
            document.getElementById("price_tag"+count).value = value - 1;
            document.getElementById("price"+count).innerHTML = parseInt(price) * (value - 1);
            document.getElementById("content_quantity"+count).innerHTML = value - 1;

            if(document.getElementById("sub_checkbox"+count).checked == true)
            {
                document.getElementById("total_price").innerHTML = parseInt(document.getElementById("total_price").innerHTML) - parseInt(price);
            }
        }
    }
}