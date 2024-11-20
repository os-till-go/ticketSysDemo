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