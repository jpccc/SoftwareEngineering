'use strict';

var placeholders = document.querySelectorAll('.styled-input__placeholder-text'),
    inputs = document.querySelectorAll('.styled-input__input');

placeholders.forEach(function (el, i) {
    var value = el.innerText,
        html = '';
    for (var _iterator = value, _isArray = Array.isArray(_iterator), _i = 0, _iterator = _isArray ? _iterator : _iterator[Symbol.iterator]();;) {
        var _ref;

        if (_isArray) {
            if (_i >= _iterator.length) break;
            _ref = _iterator[_i++];
        } else {
            _i = _iterator.next();
            if (_i.done) break;
            _ref = _i.value;
        }

        var w = _ref;

        if (!value) value = '&nbsp;';
        html += '<span class="letter">' + w + '</span>';
    }
    el.innerHTML = html;
});

inputs.forEach(function (el) {
    var parent = el.parentNode;
    el.addEventListener('focus', function () {
        parent.classList.add('filled');
        placeholderAnimationIn(parent, true);
    }, false);
    el.addEventListener('blur', function () {
        if (el.value.length) return;
        parent.classList.remove('filled');
        placeholderAnimationIn(parent, false);
    }, false);
});

function placeholderAnimationIn(parent, action) {
    var act = action ? 'add' : 'remove';
    var letters = parent.querySelectorAll('.letter');
    letters = [].slice.call(letters, 0);
    if (!action) letters = letters.reverse();
    letters.forEach(function (el, i) {
        setTimeout(function () {
            var contains = parent.classList.contains('filled');
            if (action && !contains || !action && contains) return;
            el.classList[act]('active');
        }, 50 * i);
    });
}

setTimeout(function () {
    document.body.classList.add('on-start');
}, 100);

setTimeout(function () {
    document.body.classList.add('document-loaded');
}, 1800);

//定位提交按钮
var inputSet=document.getElementsByTagName("input");
var inputElement = inputSet[2];
//为提交按钮添加单击事件
document.getElementById("submit").onclick= function(){
        //定位<form>标签，forms表示document对象中所有表单的集合，通过下标引用不同的表单，从0开始
        var username=inputSet[0].value;
        var password= inputSet[1].value;
        console.log(username);
        console.log(password);
        if(username==""||(username[0]!='S'&&username[0]!='P'&&username[0]!='R')){
            alert("用户名为空或非法");
        }else if(password==""){
            alert("请输入密码");
        }
        else {
            var formElement = document.getElementsByTagName("form")[0];
            //提交表单，提交到action属性指定的地方
            formElement.submit();
        }
    }