# **1. CI/CD란?**

- **CI(Continuous Integration, 지속적 통합)**: 코드가 저장소에 병합될 때마다 **자동으로 빌드·테스트**하여, 오류를 빨리 발견하고 일관된 빌드 산출물을 만든다.
- **CD(Continuous Delivery/Deployment, 지속적 제공/배포)**: CI를 통과한 산출물을 **자동으로 패키징→배포(또는 배포 준비)**까지 이어 주는 단계. 본 프로젝트는 “메인 브랜치 푸시 시 자동 배포”이므로 **Continuous Deployment**에 가깝다.

# **2. BE:OUR 프로젝트의 CI/CD 구성 (GitHub Actions 중심)**

- 공통
    - **트리거**: `main` 브랜치에 **push/merge** 발생 시.
    - **알림**: PR/Discussions/배포 결과를 **Discord Webhook**으로 팀에 실시간 공유.
- **백엔드 파이프라인 (EC2·Docker 배포)**
    1. **Checkout & JDK 세팅** → Gradle **빌드**
    2. **단위/통합 테스트 실행** (CI 핵심)
    3. **Docker 이미지 생성** (애플리케이션 패키징)
    4. **EC2 배포**
        - EC2에 이미지/아티팩트를 전달하고 **컨테이너 재기동**(예: `docker compose up -d --pull always` 또는 컨테이너 롤링 재시작)
    5. **후처리**: Health check, 결과를 **Discord**로 통지
- **프론트엔드 파이프라인 (S3·CloudFront 배포)**
    1. **Checkout & Node 세팅** → `npm ci` 후 **빌드**
    2. **산출물 업로드**: **S3 정적 호스팅 버킷**에 업로드
    3. **캐시 무효화**: **CloudFront Invalidation**으로 전 세계 캐시 갱신
    4. **후처리**: 배포 결과를 **Discord**로 통지

> 요약: “코드 변경 → 자동 빌드·테스트(CI) → 백엔드는 Docker로 EC2에, 프론트는 S3/CloudFront에 자동 배포(CD) → Discord로 즉시 알림”의 완전 자동화 파이프라인을 설계했습니다.



# 3. 인프라 설계 흐름(요청·배포·보안 관점)

아래는 **사용자 트래픽 흐름**과 **배포·보안 흐름**을 단계별로 정리한 것입니다.

## A. 프론트엔드(정적 웹) 요청 흐름

1. 사용자가 `frontend.beour.store` 접속
2. **Route53**이 도메인을 **CloudFront**로 라우팅
3. **CloudFront(CDN)** 가 가장 가까운 엣지에서 캐시된 정적 파일 제공
4. 캐시에 없으면 **S3 버킷(정적 호스팅)**에서 파일을 원본으로 가져와 응답
5. 프론트 앱이 API 호출 시 `api.beour.store` 등 **백엔드 도메인**으로 요청 전송

```
User ─▶ Route53 ─▶ CloudFront ─▶ S3 (정적 파일 원본)
                    ▲  ▲
                    │  └─ TLS(ACM)로 HTTPS
                    └─ 전세계 캐시

```

## B. 백엔드(API) 요청 흐름

1. 프론트(또는 클라이언트)가 `api` 도메인으로 요청
2. **Route53** → **EC2의 Nginx(리버스 프록시)** 로 라우팅
3. **Nginx**가 **HTTPS 종료(TLS)** 후, 내부 포트로 **Spring Boot 컨테이너**에 전달
4. Spring Boot 컨테이너가 **Docker 네트워크**로 연결된 **MySQL 컨테이너**와 통신
5. 처리 결과를 Nginx가 클라이언트에 반환

```
Client ▶ Route53 ▶ Nginx(EC2, HTTPS종단)
                       │
                       ▼
               Spring Boot(Container) ─── MySQL(Container)

```



# + 왜 docker를 썼는가?
 도커는 애플리케이션과 실행 환경을 컨테이너로 패키징해서 어디서나 동일하게 실행할 수 있는 플랫폼입니다.
저희는 Spring Boot와 MySQL을 각각 컨테이너로 올려서 환경 일관성을 확보했고, EC2 서버에서는 Docker만 있으면 바로 배포할 수 있게 했습니다. 또한 CI/CD 파이프라인과 잘 맞아서 빌드 결과물을 Docker 이미지로 배포해 운영 편의성을 크게 높였습니다




