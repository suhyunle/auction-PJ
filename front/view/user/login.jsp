4. login.jsp
기능: 사용자 로그인 페이지. 사용자로부터 아이디와 비밀번호를 입력받아 로그인 처리를 진행.

주요 기능:
로그인 폼 제공
로그인 인증 처리

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <style>
        body {
            background-color: white;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        form {
            padding: 20px;
            border: 5px solid transparent;
            border-radius: 10px;
            background-color: white;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            position: relative;
        }

        form::before {
            content: "";
            position: absolute;
            top: -5px;
            left: -5px;
            right: -5px;
            bottom: -5px;
            border-radius: 10px;
            background: linear-gradient(45deg, red, orange, yellow, green, blue, indigo, violet);
            z-index: -1;
        }

        label {
            font-weight: bold;
        }

        input {
            display: block;
            width: 100%;
            margin-bottom: 10px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            background: linear-gradient(45deg, red, orange, yellow, green, blue, indigo, violet);
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
            font-weight: bold;
        }

        button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>

    <nav class="navbar">
        <a href="index.jsp">
            <img src="siatbay_logo.png" alt="Logo">
        </a>
        <ul class="nav-links">
            <li><a href="home.jsp">Home</a></li>
            <li><a href="about.jsp">Menu</a></li>
            <li><a href="services.jsp">Services</a></li>
            <li><a href="contact.jsp">Contact</a></li>
        </ul>
    </nav>
    
    <form onsubmit="loginUser(event)">
        <h2>로그인</h2>
        <label>ID:</label>
        <input type="text" id="id" required>
        <label>Password:</label>
        <input type="password" id="password" required>
        <button type="submit">로그인</button>
    </form>

    <script>
        async function loginUser(event) {
            event.preventDefault();
            const id = document.getElementById("id").value;
            const password = document.getElementById("password").value;

            const response = await fetch("http://백엔드주소/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ id, password }),
            });

            if (response.ok) {
                alert("로그인 성공!");
                window.location.href = "main.html"; // 로그인 후 이동할 페이지
            } else {
                alert("로그인 실패");
            }
        }
    </script>

</body>
</html>
