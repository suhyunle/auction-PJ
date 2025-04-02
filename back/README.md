# auction-PJ
main -> view -> frontController -> N Controller -> N service -> N dao

frontController를 통한 각각의 N Controller에 접근
Contorller에서 각 service에 접근
service에서 각 dao에 접근한 뒤에 dao에서는 db를 조작하는 방식

FrontController 의 경우 기존에는 단일 service 클래스를 참조하기 위해 하나의 객체만 생성했으나
각각의 서비스가 생겼으므로 각각의 service 객체를 Beanfactory 안에 생성해 싱글톤 패턴을 유지할 계획

dao의 경우 기존에는 DRIVER URL 등의 상수를 dao안에 저장했으나 .properties 파일을 통해 보안성을 높여볼 생각

# test