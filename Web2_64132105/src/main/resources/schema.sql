-- Tạo database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'ComicDB')
BEGIN
    CREATE DATABASE ComicDB;
END
GO

USE ComicDB;
GO

-- Tạo bảng users
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[users]') AND type in (N'U'))
BEGIN
    CREATE TABLE users (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        username NVARCHAR(255) NOT NULL UNIQUE,
        password NVARCHAR(255) NOT NULL,
        email NVARCHAR(255) NOT NULL UNIQUE,
        full_name NVARCHAR(255),
        role NVARCHAR(50) NOT NULL DEFAULT 'USER',
        avatar NVARCHAR(MAX),
        active BIT NOT NULL DEFAULT 1
    );
END
GO

-- Tạo bảng categories
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[categories]') AND type in (N'U'))
BEGIN
    CREATE TABLE categories (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(255) NOT NULL UNIQUE,
        description NVARCHAR(MAX)
    );
END
GO

-- Tạo bảng comics
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[comics]') AND type in (N'U'))
BEGIN
    CREATE TABLE comics (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        title NVARCHAR(255) NOT NULL,
        description NVARCHAR(2000),
        author NVARCHAR(255),
        cover_image NVARCHAR(MAX),
        status NVARCHAR(50) NOT NULL DEFAULT 'ONGOING',
        user_id BIGINT NOT NULL,
        category_id BIGINT NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2,
        CONSTRAINT FK_Comics_Users FOREIGN KEY (user_id) REFERENCES users(id),
        CONSTRAINT FK_Comics_Categories FOREIGN KEY (category_id) REFERENCES categories(id)
    );
END
GO

-- Thêm dữ liệu mẫu cho bảng users (mật khẩu đã được mã hóa: 'admin123' và 'user123')
IF NOT EXISTS (SELECT * FROM users WHERE username = 'admin')
BEGIN
    INSERT INTO users (username, password, email, role)
    VALUES ('admin', '$2a$10$rS.bCzKFh2wd.1dC8qYx7OQI0ZlHYKrH.0zqjcS.d.IG3.KqHX8ji', 'admin@example.com', 'ADMIN');
END
GO

IF NOT EXISTS (SELECT * FROM users WHERE username = 'user')
BEGIN
    INSERT INTO users (username, password, email, role)
    VALUES ('user', '$2a$10$9.yhCVDG9ZX0ZDEh/EAOYOh.Gm4.r/wMYX9JjbBzYgr4GQEBQzDPW', 'user@example.com', 'USER');
END
GO

-- Thêm dữ liệu mẫu cho bảng categories
IF NOT EXISTS (SELECT * FROM categories WHERE name = N'Action')
BEGIN
    INSERT INTO categories (name, description)
    VALUES 
    (N'Action', N'Thể loại có nhiều cảnh hành động'),
    (N'Comedy', N'Thể loại hài hước'),
    (N'Romance', N'Thể loại lãng mạn'),
    (N'Horror', N'Thể loại kinh dị'),
    (N'Adventure', N'Thể loại phiêu lưu');
END
GO 