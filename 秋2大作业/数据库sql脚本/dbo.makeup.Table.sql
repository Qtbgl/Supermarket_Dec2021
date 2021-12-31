USE [supermarket]
GO
/****** Object:  Table [dbo].[makeup]    Script Date: 2021/12/31 23:33:55 ******/
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
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1223, 2145, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1223, 2147, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1224, 2146, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1224, 2148, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1224, 2149, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1226, 2151, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1226, 2152, 2)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1232, 2153, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1233, 2158, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1233, 2159, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1238, 2154, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1238, 2158, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1244, 2162, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1244, 2163, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1245, 2160, 1)
INSERT [dbo].[makeup] ([goods_id], [sale_id], [number_n]) VALUES (1245, 2163, 1)
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
