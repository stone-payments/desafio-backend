CREATE DATABASE [AuthDb]
GO
USE [AuthDb]
GO
CREATE TABLE [dbo].[User](
	[Id] [int] IDENTITY PRIMARY KEY NOT NULL,
	[Login] [varchar](100) NOT NULL,
	[Password] [varchar](100) NOT NULL,
	)
GO