//기능: 사용자 회원가입 페이지. 
사용자가 신규 계정을 만들 수 있도록 양식을 제공.

회원가입 폼 제공
사용자 정보 입력 및 계정 생성

회원가입 폼 (이름, 비밀번호 입력)
 입력값 유효성 검사 (비밀번호 길이등)
회원가입 요청을 서버로 전송 (POST /signup)
<form onsubmit="registerUser(event)">
    <nav class="navbar">
        <a href="index.jsp">
            <img src="siatbay_logo.png" alt="Logo">
        </a>
     
        </ul>
    </nav>
    <label>ID:</label><input type="text" id="id" required><br>
    <label>Password:</label><input type="password" id="password" required><br>
    <label>이름:</label><input type="text" id="name" required><br>
    <button type="submit">회원가입</button>
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
    
    
</form>
<script>
    
    async function registerUser(event) {
        event.preventDefault();
        const id = document.getElementById("id").value;
        const password = document.getElementById("password").value;
        const name = document.getElementById("name").value;

        const response = await fetch("http://백엔드주소/api/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, password, name }),
        });

        if (response.ok) {
            alert("회원가입 성공!");
            window.location.href = "login.html";
        } else {
            alert("회원가입 실패");
        }
    }
</script>
