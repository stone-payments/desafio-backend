USE [StarStore]
GO
CREATE TABLE [dbo].[Client](
	[Id] [int] IDENTITY PRIMARY KEY NOT NULL,
	[ClientId] [varchar](100) NOT NULL,
	[ClientName] [varchar](100) NOT NULL,
	)
GO