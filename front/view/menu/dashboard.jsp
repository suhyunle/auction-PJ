dashboard.jsp	
로그인 후 메인 화면 (경매 목록, 마이페이지, 결제 내역 등으로 이동)
// 로그인 후 메인 화면
// 1. 경매 물품보기
2. 경매 물품등록
3. 경매참여
4. 로그아웃
5. 거래내역확인 
6. 결제
// 현재 진행 중인 경매 하이라이트 표시

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            padding: 20px;
        }
        .navbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: #ffffff;
            padding: 10px 20px;
            margin-bottom: 20px;
            border-radius: 8px;
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
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        .menu {
            list-style: none;
            padding: 0;
        }
        .menu li {
            margin: 10px 0;
        }
        .menu a {
            display: block;
            padding: 10px;
            background: hsl(0, 0%, 87%);
            color: rgb(0, 0, 0);
            text-decoration: none;
            text-align: center;
            border-radius: 5px;
        }
        .menu a:hover {
            background: #0056b3;
        }
        .auction-highlight {
            margin-top: 20px;
            padding: 15px;
            background: #ffc107;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
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
    <div class="container">
        <h1>Dashboard</h1>
        <ul class="menu">
            <li><a href="list.html">경매 물품 보기</a></li>
            <li><a href="register-auction.html">경매 물품 등록</a></li>
            <li><a href="participate-auction.html">경매 참여</a></li>
            <li><a href="transaction-history.html">거래 내역 확인</a></li>
            <li><a href="payment.html">결제</a></li>
            <li><a href="logout.html">로그아웃</a></li>
        </ul>
        <div class="auction-highlight">
            현재 진행 중인 경매: [경매 하이라이트 표시]
        </div>
    </div>
</body>
</html>
