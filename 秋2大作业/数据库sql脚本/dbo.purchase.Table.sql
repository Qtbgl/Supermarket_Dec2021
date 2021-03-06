USE [supermarket]
GO
/****** Object:  Table [dbo].[purchase]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[purchase](
	[purchase_id] [int] IDENTITY(5123,1) NOT NULL,
	[sale_id] [int] NOT NULL,
	[customer_id] [int] NOT NULL,
	[purchase_date] [datetime] NULL,
	[number_s] [int] NOT NULL,
 CONSTRAINT [PK_purchase] PRIMARY KEY CLUSTERED 
(
	[purchase_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[purchase] ON 

INSERT [dbo].[purchase] ([purchase_id], [sale_id], [customer_id], [purchase_date], [number_s]) VALUES (5126, 2151, 3193, NULL, 5)
SET IDENTITY_INSERT [dbo].[purchase] OFF
GO
ALTER TABLE [dbo].[purchase]  WITH CHECK ADD  CONSTRAINT [FK_purchase_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([customer_id])
GO
ALTER TABLE [dbo].[purchase] CHECK CONSTRAINT [FK_purchase_customer]
GO
ALTER TABLE [dbo].[purchase]  WITH CHECK ADD  CONSTRAINT [FK_purchase_sale] FOREIGN KEY([sale_id])
REFERENCES [dbo].[sale] ([sale_id])
GO
ALTER TABLE [dbo].[purchase] CHECK CONSTRAINT [FK_purchase_sale]
GO
