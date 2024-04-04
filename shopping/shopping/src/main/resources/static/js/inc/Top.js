function move_page(value)
{
    if(value == "op0")
    {
        location.href = '/admin/ItemType';
    }
    else if(value == "op1")
    {
        location.href = '/admin/ItemCategory';
    }
    else if(value == "op2")
    {
        location.href = '/admin/ItemRegister';
    }
    else
    {
        location.href = '/admin/ItemList';
    }
}