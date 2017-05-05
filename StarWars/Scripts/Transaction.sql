USE [StarStore]
GO

CREATE TABLE [dbo].[Transaction](
	[Id] [int] IDENTITY PRIMARY KEY NOT NULL,
	[Client_id] [varchar](50) NOT NULL,
	[Client_name] [varchar](50) NULL,
	[Total_to_pay] INT NOT NULL,
	[Purchase_Id] UNIQUEIDENTIFIER NOT NULL,
	[TransactionDate] [varchar](30) NOT NULL)
GO

CREATE TABLE [dbo].[CreditCard_Transaction](
	[Id] [int] IDENTITY PRIMARY KEY NOT NULL,
	[Card_number] [varchar](50) NOT NULL,
	[Value] INT NOT NULL,
	[Cvv] INT NOT NULL,
	[Card_holder_name] [varchar](50) NOT NULL,
	[Exp_date]  [varchar](50)NOT NULL)
GO

ALTER TABLE [CreditCard_Transaction] ADD [TransactionId] INT NOT NULL 
ALTER TABLE [CreditCard_Transaction]
ADD CONSTRAINT TransactionId
FOREIGN KEY (TransactionId) REFERENCES [Transaction](Id)