USE [supermarket]
GO
/****** Object:  Table [dbo].[sale]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sale](
	[sale_id] [int] IDENTITY(2145,1) NOT NULL,
	[sale_name] [varchar](50) NOT NULL,
	[sale_price] [decimal](18, 2) NOT NULL,
	[sale_pid] [int] NULL,
	[logout] [int] NOT NULL,
 CONSTRAINT [PK_sale] PRIMARY KEY CLUSTERED 
(
	[sale_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[sale] ON 

INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2145, N'红茶饮料', CAST(4.00 AS Decimal(18, 2)), 2147, 1)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2146, N'农夫山泉', CAST(3.30 AS Decimal(18, 2)), 2149, 1)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2147, N'红茶饮料', CAST(4.20 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2148, N'农夫山泉', CAST(3.00 AS Decimal(18, 2)), 2149, 1)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2149, N'农夫山泉', CAST(3.10 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2151, N'火腿肠', CAST(1.50 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2152, N'双份火腿肠', CAST(2.50 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2153, N'雪碧', CAST(3.60 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2154, N'薯片', CAST(5.00 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2158, N'可口可乐薯片', CAST(7.50 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2159, N'可口可乐', CAST(3.00 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2160, N'沐浴乳', CAST(19.00 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2162, N'洗发液', CAST(15.00 AS Decimal(18, 2)), NULL, 0)
INSERT [dbo].[sale] ([sale_id], [sale_name], [sale_price], [sale_pid], [logout]) VALUES (2163, N'洗漱用品', CAST(30.00 AS Decimal(18, 2)), NULL, 0)
SET IDENTITY_INSERT [dbo].[sale] OFF
GO
ALTER TABLE [dbo].[sale] ADD  CONSTRAINT [DF_sale_logout]  DEFAULT ((0)) FOR [logout]
GO
ALTER TABLE [dbo].[sale]  WITH CHECK ADD  CONSTRAINT [FK_sale_pid] FOREIGN KEY([sale_pid])
REFERENCES [dbo].[sale] ([sale_id])
GO
ALTER TABLE [dbo].[sale] CHECK CONSTRAINT [FK_sale_pid]
GO
ALTER TABLE [dbo].[sale]  WITH CHECK ADD  CONSTRAINT [CK_sale_logout] CHECK  (([logout]>=(0) AND [logout]<=(1)))
GO
ALTER TABLE [dbo].[sale] CHECK CONSTRAINT [CK_sale_logout]
GO
ALTER TABLE [dbo].[sale]  WITH CHECK ADD  CONSTRAINT [CK_sale_pid_logout] CHECK  (([sale_pid] IS NULL OR [sale_pid] IS NOT NULL AND [logout]=(1)))
GO
ALTER TABLE [dbo].[sale] CHECK CONSTRAINT [CK_sale_pid_logout]
GO
ALTER TABLE [dbo].[sale]  WITH CHECK ADD  CONSTRAINT [CK_sale_price] CHECK  (([sale_price]>=(0)))
GO
ALTER TABLE [dbo].[sale] CHECK CONSTRAINT [CK_sale_price]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'0-上架中，1-被下架' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sale', @level2type=N'CONSTRAINT',@level2name=N'CK_sale_logout'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'sale_pid是迭代品，一定下架' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sale', @level2type=N'CONSTRAINT',@level2name=N'CK_sale_pid_logout'
GO
