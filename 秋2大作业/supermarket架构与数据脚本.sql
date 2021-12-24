USE [supermarket]
GO
/****** Object:  Table [dbo].[customer]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customer](
	[customer_id] [int] IDENTITY(3175,1) NOT NULL,
	[customer_name] [varchar](50) NOT NULL,
	[customer_pwd] [varchar](50) NOT NULL,
	[customer_vip] [int] NOT NULL,
 CONSTRAINT [PK_customer] PRIMARY KEY CLUSTERED 
(
	[customer_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[goods]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[goods](
	[goods_id] [int] IDENTITY(1223,1) NOT NULL,
	[goods_name] [varchar](50) NOT NULL,
	[goods_type] [varchar](50) NOT NULL,
	[number_c] [int] NOT NULL,
 CONSTRAINT [PK_goods] PRIMARY KEY CLUSTERED 
(
	[goods_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[import]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[import](
	[import_id] [int] IDENTITY(4121,1) NOT NULL,
	[goods_id] [int] NOT NULL,
	[import_date] [datetime] NULL,
	[number_a] [int] NOT NULL,
 CONSTRAINT [PK_import] PRIMARY KEY CLUSTERED 
(
	[import_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[makeup]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[makeup](
	[goods_id] [int] NOT NULL,
	[sale_id] [int] NOT NULL,
	[number_n] [int] NOT NULL,
 CONSTRAINT [PK_makeup] PRIMARY KEY CLUSTERED 
(
	[goods_id] ASC,
	[sale_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[purchase]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[purchase](
	[purchase_id] [int] IDENTITY(5123,1) NOT NULL,
	[goods_id] [int] NOT NULL,
	[customer_id] [int] NOT NULL,
	[number_s] [int] NOT NULL,
 CONSTRAINT [PK_purchase] PRIMARY KEY CLUSTERED 
(
	[purchase_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[remove]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[remove](
	[remove_id] [int] IDENTITY(6043,1) NOT NULL,
	[goods_id] [int] NOT NULL,
	[remove_date] [datetime] NULL,
	[number_o] [int] NOT NULL,
 CONSTRAINT [PK_remove] PRIMARY KEY CLUSTERED 
(
	[remove_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sale]    Script Date: 2021/12/24 14:57:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sale](
	[sale_id] [int] IDENTITY(2145,1) NOT NULL,
	[sale_name] [varchar](50) NOT NULL,
	[sale_price] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_sale] PRIMARY KEY CLUSTERED 
(
	[sale_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[customer] ON 

INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip]) VALUES (3175, N'小明', N'12uufd', 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip]) VALUES (3176, N'小李', N'fmk322', 0)
INSERT [dbo].[customer] ([customer_id], [customer_name], [customer_pwd], [customer_vip]) VALUES (3180, N'小乌', N'23bbf2', 0)
SET IDENTITY_INSERT [dbo].[customer] OFF
GO
SET IDENTITY_INSERT [dbo].[goods] ON 

INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c]) VALUES (1223, N'茶Pi', N'饮料', 4)
INSERT [dbo].[goods] ([goods_id], [goods_name], [goods_type], [number_c]) VALUES (1224, N'农夫山泉', N'饮料', 7)
SET IDENTITY_INSERT [dbo].[goods] OFF
GO
SET IDENTITY_INSERT [dbo].[import] ON 

INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4122, 1223, NULL, 4)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4124, 1224, NULL, 6)
INSERT [dbo].[import] ([import_id], [goods_id], [import_date], [number_a]) VALUES (4126, 1224, NULL, 1)
SET IDENTITY_INSERT [dbo].[import] OFF
GO
SET IDENTITY_INSERT [dbo].[purchase] ON 

INSERT [dbo].[purchase] ([purchase_id], [goods_id], [customer_id], [number_s]) VALUES (5124, 1223, 3176, 5)
SET IDENTITY_INSERT [dbo].[purchase] OFF
GO
SET IDENTITY_INSERT [dbo].[remove] ON 

INSERT [dbo].[remove] ([remove_id], [goods_id], [remove_date], [number_o]) VALUES (6044, 1223, NULL, 30)
INSERT [dbo].[remove] ([remove_id], [goods_id], [remove_date], [number_o]) VALUES (6045, 1224, NULL, 10)
SET IDENTITY_INSERT [dbo].[remove] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [C2]    Script Date: 2021/12/24 14:57:36 ******/
ALTER TABLE [dbo].[goods] ADD  CONSTRAINT [C2] UNIQUE NONCLUSTERED 
(
	[goods_name] ASC,
	[goods_type] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[customer] ADD  CONSTRAINT [DF_customer_customer_vip]  DEFAULT ((0)) FOR [customer_vip]
GO
ALTER TABLE [dbo].[import]  WITH CHECK ADD  CONSTRAINT [FK_import_goods] FOREIGN KEY([goods_id])
REFERENCES [dbo].[goods] ([goods_id])
GO
ALTER TABLE [dbo].[import] CHECK CONSTRAINT [FK_import_goods]
GO
ALTER TABLE [dbo].[makeup]  WITH CHECK ADD  CONSTRAINT [FK_makeup_goods] FOREIGN KEY([goods_id])
REFERENCES [dbo].[goods] ([goods_id])
GO
ALTER TABLE [dbo].[makeup] CHECK CONSTRAINT [FK_makeup_goods]
GO
ALTER TABLE [dbo].[makeup]  WITH CHECK ADD  CONSTRAINT [FK_makeup_sale] FOREIGN KEY([sale_id])
REFERENCES [dbo].[sale] ([sale_id])
GO
ALTER TABLE [dbo].[makeup] CHECK CONSTRAINT [FK_makeup_sale]
GO
ALTER TABLE [dbo].[purchase]  WITH CHECK ADD  CONSTRAINT [FK_purchase_customer] FOREIGN KEY([customer_id])
REFERENCES [dbo].[customer] ([customer_id])
GO
ALTER TABLE [dbo].[purchase] CHECK CONSTRAINT [FK_purchase_customer]
GO
ALTER TABLE [dbo].[purchase]  WITH CHECK ADD  CONSTRAINT [FK_purchase_goods] FOREIGN KEY([goods_id])
REFERENCES [dbo].[goods] ([goods_id])
GO
ALTER TABLE [dbo].[purchase] CHECK CONSTRAINT [FK_purchase_goods]
GO
ALTER TABLE [dbo].[remove]  WITH CHECK ADD  CONSTRAINT [FK_remove_goods] FOREIGN KEY([goods_id])
REFERENCES [dbo].[goods] ([goods_id])
GO
ALTER TABLE [dbo].[remove] CHECK CONSTRAINT [FK_remove_goods]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [CK_customer_pwd] CHECK  ((len([customer_pwd])=(6)))
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [CK_customer_pwd]
GO
ALTER TABLE [dbo].[customer]  WITH CHECK ADD  CONSTRAINT [CK_customer_vip] CHECK  (([customer_vip]>=(0) AND [customer_vip]<=(3)))
GO
ALTER TABLE [dbo].[customer] CHECK CONSTRAINT [CK_customer_vip]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'规定顾客密码是6位字符串' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'customer', @level2type=N'CONSTRAINT',@level2name=N'CK_customer_pwd'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'规定vip最多3级' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'customer', @level2type=N'CONSTRAINT',@level2name=N'CK_customer_vip'
GO
