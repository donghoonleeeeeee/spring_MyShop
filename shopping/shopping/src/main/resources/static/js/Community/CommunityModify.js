function file_click1()
    {
        document.getElementById("register_file1").click();
    }
function file_click2()
    {
        document.getElementById("register_file2").click();
    }
function file_click3()
    {
        document.getElementById("register_file3").click();
    }
function Preview1(value)
{
    var reader = new FileReader();
    reader.readAsDataURL(value.files[0]);
    reader.onload = function(e) {
    document.getElementById("image1").style.display = "inline";
    document.getElementById("image1").setAttribute("src",e.target.result);
    document.getElementById("register_file_btn1").style.display = "none";
    }
}
function Preview2(value)
{
    var reader = new FileReader();
    reader.readAsDataURL(value.files[0]);
    reader.onload = function(e) {
    document.getElementById("image2").style.display = "inline";
    document.getElementById("image2").setAttribute("src",e.target.result);
    document.getElementById("register_file_btn2").style.display = "none";
    }
}
function Preview3(value)
{
    var reader = new FileReader();
    reader.readAsDataURL(value.files[0]);
    reader.onload = function(e) {
    document.getElementById("image3").style.display = "inline";
    document.getElementById("image3").setAttribute("src",e.target.result);
    document.getElementById("register_file_btn3").style.display = "none";
    }
}
function change_image1()
{
    document.getElementById("register_file1").click();
}
function change_image2()
{
    document.getElementById("register_file2").click();
}
function change_image3()
{
    document.getElementById("register_file3").click();
}