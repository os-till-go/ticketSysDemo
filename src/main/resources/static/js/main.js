window.onload = function() {
    // 隐藏某些初始不需要显示的元素，比如票务详情页面的编辑按钮（假设只有满足一定条件才显示）
    var editButton = document.querySelector('.ticket-details.btn-warning');
    editButton.style.display = 'none';
};
function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}

var emailInput = document.getElementById('email');
emailInput.addEventListener('blur', function() {
    if (!validateEmail(this.value)) {
        alert('请输入正确的邮箱格式');
    }
});
document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('form');
    loginForm.addEventListener('submit', function (e) {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        // 使用fetch等方式发送AJAX请求到后端登录接口，如：
        fetch('/login', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username: username, password: password})
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // 登录成功，可进行页面跳转等操作，比如显示欢迎弹窗等
                } else {
                    // 登录失败，显示错误提示弹窗
                }
            });
    });
});