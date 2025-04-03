 //네비게이션 메뉴 (홈, 마이페이지, 경매, 결제 등 링크)
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Navigation Bar</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
        }
        .navbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: #f2eded;
            padding: 10px 20px;
        }
        .navbar img {
            height: 50px;
        }
        .nav-links {
            list-style: none;
            display: flex;
        }
        .nav-links li {
            margin: 0 15px;
        }
        .nav-links a {
            color: rgb(0, 0, 0);
            text-decoration: none;
            font-size: 18px;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <img src="/siatbay_logo.png" alt="Logo">
        <ul class="nav-links">
            <li><a href="dashboard.jsp">Dashboard</a></li>
            <li><a href="about.jsp">About</a></li>
            <li><a href="services.jsp">Services</a></li>
            <li><a href="contact.jsp">Contact</a></li> <!-- Contact 페이지 수정 -->
        </ul>
    </nav>
</body>
</html>
