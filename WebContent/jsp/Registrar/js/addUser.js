function subInfo() {
    var s_id = document.getElementsByName("s_id");
    var s_name=document.getElementsByName("s_name")
    var birthday=document.getElementsByName("birthday");
    var identify_num=document.getElementsByName("identify_num");
    var status=document.getElementsByName("status");
    var dept_id=document.getElementsByName("dept_id");
    var graduate_date=document.getElementsByName("graduate_date");
    var password=document.getElementsByName("password");
    if(s_name[0]==null||s_name[0].value==""){
        alert("please input name!");
        s_name[0].focus();
        return false;
    }
    if(birthday[0]==null||birthday[0].value=="") {
        alert("please input birthday!");
        birthday[0].focus();
        return false;
    }
    if(identify_num[0]==null||identify_num[0].value=="") {
        alert("please input identify num!");
        identify_num[0].focus();
        return false;
    }
    if(status[0]==null||status[0].value=="") {
        alert("please input status!");
        status[0].focus();
        return false;
    }
    if(dept_id[0]==null||dept_id[0].value=="") {
        alert("please input dept_id!");
        dept_id[0].focus();
        return false;
    }
    if(graduate_date[0]==null||graduate_date[0].value=="") {
        alert("please input graduate_date!");
        graduate_date[0].focus();
        return false;
    }
    if(password[0]==null||password[0].value==""||password[0].value.length<6) {
        alert("Password format is error");
        password[0].focus();
        return false;
    }
    return true;
}