USE [supermarket]
GO
/****** Object:  Table [dbo].[makeup]    Script Date: 2022/1/2 15:50:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[makeup](
	[sale_id] [int] NOT NULL,
	[goods_id] [int] NOT NULL,
	[number_n] [int] NOT NULL,
 CONSTRAINT [PK_makeup] PRIMARY KEY CLUSTERED 
(
	[sale_id] ASC,
	[goods_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2145, 1223, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2146, 1224, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2147, 1223, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2148, 1224, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2149, 1224, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2151, 1226, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2152, 1226, 2)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2153, 1232, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2154, 1238, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2158, 1233, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2158, 1238, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2159, 1233, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2160, 1245, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2162, 1244, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2163, 1244, 1)
INSERT [dbo].[makeup] ([sale_id], [goods_id], [number_n]) VALUES (2163, 1245, 1)
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
