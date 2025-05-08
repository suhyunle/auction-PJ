# 🧾 온라인 경매 시스템 (Online Auction System)


> JDBC & MVC 패턴 기반의 콘솔/웹 기반 온라인 경매 플랫폼  
> 사용자 간의 실시간 입찰, 상품 등록, 낙찰, 결제까지 구현된 경매 시뮬레이션 시스템입니다.
>
> <img src="https://github.com/user-attachments/assets/c34e733e-882f-4cbd-9c67-d254461b0aea" width="700">
---

## 🛠 기술 스택

<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white">  <img src="https://img.shields.io/badge/JDBC-003B57?style=for-the-badge&logo=java&logoColor=white">  <img src="https://img.shields.io/badge/Oracle%20DB-F80000?style=for-the-badge&logo=oracle&logoColor=white">  <img src="https://img.shields.io/badge/MVC%20Architecture-239120?style=for-the-badge&logoColor=white">  <img src="https://img.shields.io/badge/Singleton%20Pattern-6DB33F?style=for-the-badge&logoColor=white">  <img src="https://img.shields.io/badge/Console%20UI-808080?style=for-the-badge&logoColor=white">  

## 🧩 프로젝트 개요

- 사용자 간 실시간 입찰을 지원하는 온라인 경매 시스템
- 경매 상품 등록부터 입찰, 낙찰, 결제까지 전 과정 흐름 구현
- 콘솔 기반 또는 웹 기반으로 구현 가능

---
## 멤버
  | 직책 | 담당 | 이름 | github id  | 취미 | |
|--|--|--|--|--|--|
| 팀장 | FE | 이수현 |  [@suhyunle](https://github.com/suhyunle)| 코딩 | <img src="https://github.com/user-attachments/assets/711c7565-9d40-4e93-8c7d-198bcb7422bf" alt="이수현" width="200"/> |
| 팀원 | FE | 조영석 | [@zkftpfm](https://github.com/zkftpfm) | 돈 모으기 | <img src="https://github.com/user-attachments/assets/4a4ec42f-389d-4020-b925-eb2a2186011c" alt="조영석" width="150"/> |
| 팀원 | BE | 최여명 | [@yeomyeonGit](https://github.com/yeomyeonGit) | 외국어 공부 | <img src="https://github.com/user-attachments/assets/5b8157c2-e66b-4965-8401-fadfa5a839f1" alt="최여명" width="150"/> |
| 팀원 | BE | 최형규 | [@Choihyeonggyu-Q](https://github.com/Choihyeonggyu-Q) | 명상 | <img src="https://github.com/user-attachments/assets/c656ac85-652c-4981-93a8-ae1068cbd030" alt="최형규" width="200"/>  |

---
## 🛠️ 주요 기능

| 기능            | 설명 |
|----------------|------|
| 🔐 회원가입/로그인 | 사용자 등록 및 인증 |
| 📦 상품 등록/조회 | 경매 상품 등록 및 리스트 확인 |
| 💰 실시간 입찰     | 경매 상품에 입찰, 최고가 실시간 반영 |
| 📩 자동 알림       | 최고 입찰자 변경 시 차순위 사용자 알림 |
| 🏁 낙찰 처리       | 경매 종료 시 낙찰자 자동 지정 |
| 💳 결제 시스템     | 낙찰 후 결제 및 거래내역 저장 |

---
## 테이블구조

<img src ="https://github.com/user-attachments/assets/2be3ddbc-a0ba-4dcb-9252-df4771bf12c1" width="700">

1. `member` - 회원 정보 : 회원 고유 ID, 로그인 정보, 잔액 등을 관리합니다.
2. `auction_item` - 경매 상품 : 회원이 등록한 경매 상품 정보와 마감 시간을 관리합니다.
3. `bid` - 입찰 정보 : 특정 경매 상품에 대해 회원이 입찰한 가격과 시점을 저장합니다.
4. `payment` - 결제 내역 : 낙찰 후 결제 정보 및 결제 상태를 기록
5. 5. `category` - 카테고리 정보 : 경매 상품의 분류를 관리합니다.

## 🔗 테이블 관계 

- `member` 1 ⬌ N `auction_item`  
- `auction_item` 1 ⬌ N `bid`  
- `bid` 1 ⬌ 1 `payment`  
- `auction_item` N ⬌ 1 `category`

- 
---
## 🧱 시스템 아키텍처


- **패턴**: MVC 아키텍처
- **DB 연결**: JDBC (Java Database Connectivity)
- **API 통신**: RESTful 방식 (웹 구현 시)
- **알림 기능**: 비동기 처리 (옵션)

---
## ⚙️ 백엔드 구조 및 설계

### 🧭 전체 흐름

main → view → FrontController → N Controller → N Service → N DAO → DB



본 프로젝트는 **MVC 아키텍처**를 기반으로, 사용자 요청부터 DB 조작까지 다음과 같은 흐름으로 처리하였습니다. 

- **`main`**: 애플리케이션의 시작점으로, 사용자에게 첫 화면(입찰, 상품 목록 등)을 출력하는 `view`를 실행합니다.
- **`view`**: 사용자로부터 입력(예: 입찰가, 상품 등록 정보 등)을 받아 `FrontController`에 전달합니다.
- **`FrontController`**: 모든 사용자 요청을 중앙에서 받아 분기 처리하며, 적절한 `Controller`(예: 입찰, 상품 등록, 결제 등)로 전달합니다. (*DispatcherServlet 역할*)
- **`Controller`**: 기능별로 구성된 컨트롤러로, 사용자의 요청을 해당 `Service`에 위임합니다.  
  예: `AuctionController`, `ProductController`, `UserController` 등
- **`Service`**: 경매 로직을 처리하는 비즈니스 계층입니다.  
  예를 들어, 입찰가 비교 후 최고가 갱신, 낙찰 시간 계산, 결제 조건 검증 등의 처리를 담당합니다.
- **`DAO`**: 실제 DB에 접근하여 경매 상품, 입찰 내역, 회원 정보 등을 저장하거나 조회합니다.


### 예시흐름
입찰 요청 → view → FrontController → AuctionController
          → AuctionService → AuctionDAO → DB에 입찰 정보 저장 및 최고가 갱신


---

### 🧱 설계 개선 및 특징

#### ✅ FrontController 구조 개선

- 기존에는 단일 Service 객체만을 직접 생성해 사용
- 기능 확장에 따라 다수의 Service 객체가 생기면서, **BeanFactory에서 Service 객체를 생성·관리**하도록 개선
- 이를 통해 **싱글톤 패턴 유지**, 객체 재사용, 메모리 효율성 확보

```java
// 예시: BeanFactory 내부
public class BeanFactory {
    private static final Map<String, Object> objMap = new HashMap<>();

    static {
        objMap.put("productService", new ProductService());
        objMap.put("userService", new UserService());
        // ... 필요한 서비스 객체 추가
    }

    public static Object getBean(String key) {
        return objMap.get(key);
    }
}
