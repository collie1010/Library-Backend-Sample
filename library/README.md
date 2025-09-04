# Library Backend Sample

這是一個使用 Spring Boot 開發的圖書館後端範例專案，提供管理圖書、作者、借閱者和借閱記錄的 RESTful API。

## 功能

*   **作者管理**: 建立、讀取、更新、刪除作者資訊。
*   **圖書管理**: 建立、讀取、更新、刪除圖書資訊，並可關聯作者。
*   **借閱者管理**: 建立、讀取、更新、刪除借閱者資訊。
*   **借閱記錄管理**: 建立、讀取、更新、刪除借閱記錄，包括借閱日期和歸還日期。
*   **錯誤處理**: 全局異常處理機制，提供友善的錯誤回應。

## 技術棧

*   **Spring Boot**: 快速開發 Spring 應用程式的框架。
*   **Spring Data JPA**: 簡化資料庫操作，提供 Repository 介面。
*   **Hibernate**: JPA 實作，用於物件關係映射 (ORM)。
*   **Maven**: 專案管理和建構工具。
*   **H2 Database**: 開發和測試階段使用的內存資料庫。
*   **Lombok**: 簡化 Java 程式碼，自動生成 Getter/Setter 等。

## 專案結構

```
src/main/java/com/example/
├── controller/        # 處理 HTTP 請求的 REST 控制器
├── demo/              # 應用程式主類
├── dto/               # 資料傳輸物件 (Data Transfer Objects)
├── entity/            # 資料庫實體類
├── exception/         # 自定義異常和全局異常處理
├── repository/        # 資料庫操作介面 (Spring Data JPA)
└── service/           # 業務邏輯層介面和實作
```

## 如何運行

### 前置條件

*   Java 17 或更高版本
*   Maven 3.6.0 或更高版本

### 建構專案

在專案根目錄下執行以下命令：

```bash
mvn clean install
```

### 運行應用程式

```bash
mvn spring-boot:run
```

應用程式將在 `http://localhost:8080` 啟動。

## API 端點 (部分範例)

以下是一些主要的 API 端點範例：

### 作者 (Authors)

*   `GET /api/authors`: 獲取所有作者
*   `GET /api/authors/{id}`: 獲取指定 ID 的作者
*   `POST /api/authors`: 建立新作者
*   `PUT /api/authors/{id}`: 更新指定 ID 的作者
*   `DELETE /api/authors/{id}`: 刪除指定 ID 的作者

### 圖書 (Books)

*   `GET /api/books`: 獲取所有圖書
*   `GET /api/books/{id}`: 獲取指定 ID 的圖書
*   `POST /api/books`: 建立新圖書
*   `PUT /api/books/{id}`: 更新指定 ID 的圖書
*   `DELETE /api/books/{id}`: 刪除指定 ID 的圖書

### 借閱者 (Borrowers)

*   `GET /api/borrowers`: 獲取所有借閱者
*   `GET /api/borrowers/{id}`: 獲取指定 ID 的借閱者
*   `POST /api/borrowers`: 建立新借閱者
*   `PUT /api/borrowers/{id}`: 更新指定 ID 的借閱者
*   `DELETE /api/borrowers/{id}`: 刪除指定 ID 的借閱者

### 借閱記錄 (Loans)

*   `GET /api/loans`: 獲取所有借閱記錄
*   `GET /api/loans/{id}`: 獲取指定 ID 的借閱記錄
*   `POST /api/loans`: 建立新借閱記錄
*   `PUT /api/loans/{id}`: 更新指定 ID 的借閱記錄
*   `DELETE /api/loans/{id}`: 刪除指定 ID 的借閱記錄

## 資料庫配置

應用程式使用 H2 內存資料庫。配置位於 `src/main/resources/application.properties`。
