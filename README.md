# auction-PJ 경매시스템

## 프로젝트 개요
경매 시스템(Auction System) 프로젝트입니다. 사용자는 경매에 참여하여 입찰하고, 실시간으로 갱신되는 가격을 확인하며, 경매 종료 후 자동 낙찰이 이루어집니다. 
관리자는 경매 상품을 추가 및 관리할 수 있습니다.

## 주요 기능
- **입찰 실패 처리**: 현재 최고 입찰가보다 낮은 금액 입력 시 경고 메시지 표시
- **실시간 업데이트**: WebSocket 또는 AJAX Polling을 이용한 경매 진행 상황 갱신
- **경매 종료 후 자동 낙찰 처리**: 일정 시간이 지나면 자동으로 최고 입찰자에게 낙찰
- **관리자 페이지**: 경매 상품 추가 및 관리 기능 제공

## 브랜치 전략
프로젝트는 다음과 같은 Git 브랜치 전략을 따릅니다:

- **main**: 완벽하게 검증된 코드만 병합되는 안정적인 브랜치
- **develop**: 수정된 코드 및 새로운 기능이 추가되는 브랜치
- **function**: 각 기능별 개발을 위한 브랜치 (예: `function-bidding`, `function-admin`)

### 브랜치 관리 규칙
1. 모든 개발은 `function` 브랜치에서 진행합니다.
2. 기능이 완료되면 `develop` 브랜치로 병합합니다.
3. `develop` 브랜치에서 충분히 테스트 후 `main` 브랜치로 최종 병합합니다.

## 프로젝트 일정
- **개발 기간**: 화요일 ~ 금요일 (화수목금)
- **주요 마일스톤**
  - 1주차: 프로젝트 환경 설정 및 기본 기능 구현
  - 2주차: 실시간 업데이트 및 경매 종료 처리 구현
  - 3주차: 관리자 페이지 및 최종 테스트
  - 4주차: 코드 리뷰 및 배포 준비

## 개발 환경
- **백엔드**: Java, Spring Boot, MySQL
- **프론트엔드**: HTML, CSS, JavaScript (React.js)
- **실시간 업데이트**: WebSocket 또는 AJAX Polling
- **버전 관리**: Git & GitHub

## 기여 방법
1. `function` 브랜치에서 새로운 기능을 개발합니다.
2. 기능 개발 후 `develop` 브랜치로 PR(Pull Request)을 생성합니다.
3. 코드 리뷰 후 `develop`에 병합하고, 최종적으로 `main`에 병합한다.

