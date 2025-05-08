# 🧾 온라인 경매 시스템 (Online Auction System)

> JDBC & MVC 패턴 기반의 콘솔/웹 기반 온라인 경매 플랫폼  
> 사용자 간의 실시간 입찰, 상품 등록, 낙찰, 결제까지 구현된 경매 시뮬레이션 시스템입니다.

---

## 🧩 프로젝트 개요

- 사용자 간 실시간 입찰을 지원하는 온라인 경매 시스템
- 경매 상품 등록부터 입찰, 낙찰, 결제까지 전 과정 흐름 구현
- 콘솔 기반 또는 웹 기반으로 구현 가능

---
## 멤버
  | 직책 | 담당 | 이름 | github id  | 취미 | |
|--|--|--|--|--|--|
| 팀장 | FE | 이수현 |  [@suhyunle](https://github.com/suhyunle)| 명상 | <img src="https://github.com/user-attachments/assets/f2a2d74e-6c39-48ea-ab20-7665eae9be12" alt="이수현" width="100"/> |
| 팀원 | FE | 조영석 | [@zkftpfm](https://github.com/zkftpfm) | 명상 | <img src="https://github.com/user-attachments/assets/27a3d5e6-4a37-42e4-9393-d8e353d5b11f" alt="조영석" width="100"/> |
| 팀원 | BE | 최여명 | [@yeomyeonGit](https://github.com/yeomyeonGit) | 명상 | <img src="https://github.com/user-attachments/assets/42377928-87ab-42a1-8bf0-c09d5952bc47" alt="최여명" width="100"/> |
| 팀원 | BE | 최형규 | [@Choihyeonggyu-Q](https://github.com/Choihyeonggyu-Q) | 명상 | <img src="https://github.com/user-attachments/assets/4e55c61e-48a0-47c4-be22-9ac97b6c9f1a" alt="최형규" width="100"/>  |

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

## 🧱 시스템 아키텍처


- **패턴**: MVC 아키텍처
- **DB 연결**: JDBC (Java Database Connectivity)
- **API 통신**: RESTful 방식 (웹 구현 시)
- **알림 기능**: 비동기 처리 (옵션)

---
##BACK
main -> view -> frontController -> N Controller -> N service -> N dao

frontController를 통한 각각의 N Controller에 접근 Contorller에서 각 service에 접근 service에서 각 dao에 접근한 뒤에 dao에서는 db를 조작하는 방식

FrontController 의 경우 기존에는 단일 service 클래스를 참조하기 위해 하나의 객체만 생성했으나 각각의 서비스가 생겼으므로 각각의 service 객체를 Beanfactory 안에 생성해 싱글톤 패턴을 유지할 계획

dao의 경우 기존에는 DRIVER URL 등의 상수를 SESSION 안의 DBCONNECT 파일 하나로 유지함
